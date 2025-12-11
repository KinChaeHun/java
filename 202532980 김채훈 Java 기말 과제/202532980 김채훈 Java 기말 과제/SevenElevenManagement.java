import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*; // 파일 입출력은 이제 사용하지 않지만, 기본 임포트는 유지
import java.sql.*; // MySQL JDBC 연동을 위한 핵심 임포트
import java.util.HashMap;
import java.util.Map;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

class EventType {
    public static final String ONE_PLUS_ONE = "1+1";
    public static final String TWO_PLUS_ONE = "2+1";
    public static final String DISCOUNT = "할인";
    public static final String BUNDLE = "묶음상품";

    public static String[] getValues() {
        return new String[]{ONE_PLUS_ONE, TWO_PLUS_ONE, DISCOUNT, BUNDLE};
    }
}

class EventRule implements Serializable {
    String ruleName;
    String type;
    String targetProduct;
    int value; 

    public EventRule(String ruleName, String type, String targetProduct, int value) {
        this.ruleName = ruleName;
        this.type = type;
        this.targetProduct = targetProduct;
        this.value = value;
    }
}

class Product implements Serializable {
    String name;
    int price;
    int quantity;
    int orderedQuantity; 
    int soldQuantity; 
    int profitRate; 

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.orderedQuantity = 0;
        this.soldQuantity = 0;
        this.profitRate = 10; 
    }
    
    public Product(String name, int price, int quantity, int orderedQuantity, int soldQuantity, int profitRate) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.orderedQuantity = orderedQuantity;
        this.soldQuantity = soldQuantity;
        this.profitRate = profitRate;
    }
    
    public long calculateProfit(long finalSellingPrice) {
        double rate = this.profitRate / 100.0;
        return (long)(finalSellingPrice * rate); 
    }
}

class TransactionDetail {
    String name;
    long unitPrice;
    int quantity;
    long itemFinalPrice;
    long itemNetProfit;
    int freeCount; 
    
    public TransactionDetail(long itemNetProfit) {
        this.itemNetProfit = itemNetProfit;
    }

    public TransactionDetail(String name, long unitPrice, int quantity, long itemFinalPrice, long itemNetProfit, int freeCount) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.itemFinalPrice = itemFinalPrice;
        this.itemNetProfit = itemNetProfit;
        this.freeCount = freeCount;
    }
}

class SaleResult {
    long totalPrice;
    int freeCount;
    SaleResult(long totalPrice, int freeCount) {
        this.totalPrice = totalPrice;
        this.freeCount = freeCount;
    }
}


public class SevenElevenManagement extends JFrame {

    // 🔑 MySQL JDBC 설정 정보
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/seven_eleven_db?serverTimezone=UTC";
    private static final String USER = "seven"; // 사용자 이름
    private static final String PASS = "0000"; // 비밀번호

    private HashMap<String, Product> productDB = new HashMap<>();
    private HashMap<String, EventRule> eventDB = new HashMap<>(); 
    
    private long totalRevenue = 0; 

    private JTabbedPane tabbedPane;

    private JTextField mNameField, mPriceField, mQtyField, mProfitRateField; 
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JTextField mSearchField; 

    private JTable salesInventoryTable; 
    private DefaultTableModel salesInventoryTableModel; 
    private JTextField sSearchField; 
    private JTable cartTable; 
    private CartTableModel cartTableModel; 
    private JTextArea receiptArea;
    private JLabel totalLabel; 
    private JTextField manualDiscountField;
    private JLabel revenueProfitLabel; 
    
    private JLabel revenueSummaryLabel;
    private JTable revenueTable;
    private DefaultTableModel revenueTableModel;

    private HashMap<String, Integer> currentCart = new HashMap<>();
    
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.KOREA);

    public SevenElevenManagement() {
        setTitle("세븐일레븐 통합 관리 시스템 v5.2 (MySQL)");
        setSize(1200, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🔄 데이터 로드: CSV -> DB
        loadProductsFromDB();
        loadEventsFromDB();
        loadRevenueFromDB(); 

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // 🔄 데이터 저장: CSV -> DB
                saveProductsToDB();
                saveEventsToDB();
                saveRevenueToDB();
                System.exit(0);
            }
        });

        tabbedPane = new JTabbedPane();

        JPanel managerPanel = createManagerPanel();
        JPanel eventPanel = createEventPanel();
        JPanel salesPanel = createSalesPanel();
        JPanel revenuePanel = createRevenuePanel();
        
        tabbedPane.addTab("상품 및 재고 관리 (Back Office)", managerPanel);
        tabbedPane.addTab("이벤트 관리", eventPanel);
        tabbedPane.addTab("판매 포스 (POS)", salesPanel);
        tabbedPane.addTab("매출 및 수익 현황", revenuePanel);

        add(tabbedPane);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane.getSelectedIndex();
                if (index == 0) {
                    refreshTable(mSearchField.getText().trim());
                } else if (index == 2) { 
                    refreshSalesInventoryTable(sSearchField.getText().trim());
                    updateRevenueProfitLabelInSalesTab(); 
                } else if (index == 3) {
                    updateRevenuePanel();
                }
            }
        });

        refreshTable("");
    }
    
    // 🐘 MySQL JDBC 유틸리티 함수
    private Connection getConnection() throws Exception {
        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException se) { /* 무시 */ }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException se) { /* 무시 */ }
        try {
            if (conn != null) conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    // 🔄 데이터 로드/저장 함수 (DB 대체)
    
    private void loadProductsFromDB() {
        productDB.clear();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT name, price, quantity, ordered_quantity, sold_quantity, profit_rate FROM products");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                int orderedQuantity = rs.getInt("ordered_quantity");
                int soldQuantity = rs.getInt("sold_quantity");
                int profitRate = rs.getInt("profit_rate");
                productDB.put(name, new Product(name, price, quantity, orderedQuantity, soldQuantity, profitRate));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "상품 데이터 로드 중 DB 오류: " + e.getMessage() + "\n초기 샘플 데이터로 시작합니다.", "DB 오류", JOptionPane.ERROR_MESSAGE);
            // DB 연결 실패 시 초기 샘플 데이터 로드
            if (productDB.isEmpty()) { 
                 productDB.put("새우깡", new Product("새우깡", 1700, 50, 0, 0, 10));
                 productDB.put("콜라", new Product("콜라", 2000, 30, 0, 0, 9));
                 productDB.put("삼각김밥", new Product("삼각김밥", 1200, 15, 0, 0, 20)); 
            }
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, rs);
        }
    }

    private boolean saveProductsToDB() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO products (name, price, quantity, ordered_quantity, sold_quantity, profit_rate) " +
                         "VALUES (?, ?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE price=?, quantity=?, ordered_quantity=?, sold_quantity=?, profit_rate=?";
            stmt = conn.prepareStatement(sql);
            
            for (Product p : productDB.values()) {
                // INSERT 파라미터 (1~6)
                stmt.setString(1, p.name);
                stmt.setInt(2, p.price);
                stmt.setInt(3, p.quantity);
                stmt.setInt(4, p.orderedQuantity);
                stmt.setInt(5, p.soldQuantity);
                stmt.setInt(6, p.profitRate);
                
                // UPDATE 파라미터 (7~11)
                stmt.setInt(7, p.price);
                stmt.setInt(8, p.quantity);
                stmt.setInt(9, p.orderedQuantity);
                stmt.setInt(10, p.soldQuantity);
                stmt.setInt(11, p.profitRate);
                
                stmt.addBatch();
            }
            stmt.executeBatch();
            return true;
        } catch (Exception e) {
            System.err.println("상품 데이터 DB 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, stmt, null);
        }
    }
    
    private void loadEventsFromDB() {
        eventDB.clear();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT rule_name, rule_type, target_product, rule_value FROM event_rules");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String name = rs.getString("rule_name");
                String type = rs.getString("rule_type");
                String target = rs.getString("target_product");
                int value = rs.getInt("rule_value");
                eventDB.put(name, new EventRule(name, type, target, value));
            }
        } catch (Exception e) {
            System.err.println("이벤트 데이터 로드 중 DB 오류: " + e.getMessage());
        } finally {
            closeConnection(conn, stmt, rs);
        }
    }

    private boolean saveEventsToDB() {
        Connection conn = null;
        PreparedStatement deleteStmt = null;
        PreparedStatement insertStmt = null;
        try {
            conn = getConnection();
            // 1. 기존 이벤트 데이터를 모두 삭제 (전체 갱신)
            deleteStmt = conn.prepareStatement("DELETE FROM event_rules");
            deleteStmt.executeUpdate();
            
            // 2. 새로운 이벤트 데이터 삽입
            String sql = "INSERT INTO event_rules (rule_name, rule_type, target_product, rule_value) VALUES (?, ?, ?, ?)";
            insertStmt = conn.prepareStatement(sql);
            
            for (EventRule rule : eventDB.values()) {
                insertStmt.setString(1, rule.ruleName);
                insertStmt.setString(2, rule.type);
                insertStmt.setString(3, rule.targetProduct);
                insertStmt.setInt(4, rule.value);
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
            return true;
        } catch (Exception e) {
            System.err.println("이벤트 데이터 DB 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(null, insertStmt, null);
            closeConnection(conn, deleteStmt, null); 
        }
    }
    
    private void loadRevenueFromDB() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT total_revenue FROM revenue_data WHERE id = 1");
            rs = stmt.executeQuery();
            if (rs.next()) {
                totalRevenue = rs.getLong("total_revenue");
            } else {
                totalRevenue = 0;
            }
        } catch (Exception e) {
            System.err.println("총 매출 로드 중 DB 오류: " + e.getMessage());
            totalRevenue = 0;
        } finally {
            closeConnection(conn, stmt, rs);
        }
    }

    private boolean saveRevenueToDB() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            // INSERT OR UPDATE를 사용하여 id=1인 레코드의 total_revenue를 갱신
            String sql = "INSERT INTO revenue_data (id, total_revenue) VALUES (1, ?) ON DUPLICATE KEY UPDATE total_revenue = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, totalRevenue);
            stmt.setLong(2, totalRevenue);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("총 매출 DB 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, stmt, null);
        }
    }
    
    private JPanel createManagerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        fieldPanel.add(new JLabel("상품명:"));
        mNameField = new JTextField(10);
        fieldPanel.add(mNameField);
        fieldPanel.add(new JLabel("가격:"));
        mPriceField = new JTextField(7);
        fieldPanel.add(mPriceField);
        fieldPanel.add(new JLabel("발주/입고 수량:"));
        mQtyField = new JTextField(5);
        fieldPanel.add(mQtyField);
        fieldPanel.add(new JLabel("이익률(%):")); 
        mProfitRateField = new JTextField("10", 3); 
        fieldPanel.add(mProfitRateField);

        JButton addBtn = new JButton("신규등록");
        JButton editBtn = new JButton("정보수정"); 
        JButton delBtn = new JButton("삭제");
        JButton autoOrderListBtn = new JButton("자동 발주 목록 생성");
        JButton receiveBtn = new JButton("발주 승인 (입고)");
        JButton saveBtn = new JButton("데이터 저장 (DB)"); 

        addBtn.setBackground(new Color(70, 130, 180)); addBtn.setForeground(Color.WHITE);
        editBtn.setBackground(new Color(100, 100, 100)); editBtn.setForeground(Color.WHITE);
        delBtn.setBackground(new Color(200, 0, 0)); delBtn.setForeground(Color.WHITE);
        autoOrderListBtn.setBackground(new Color(25, 25, 112)); autoOrderListBtn.setForeground(Color.WHITE);
        receiveBtn.setBackground(new Color(34, 139, 34)); receiveBtn.setForeground(Color.WHITE);
        saveBtn.setBackground(new Color(255, 165, 0)); saveBtn.setForeground(Color.BLACK);

        btnPanel.add(addBtn); btnPanel.add(editBtn); btnPanel.add(delBtn);
        btnPanel.add(new JSeparator(SwingConstants.VERTICAL));
        btnPanel.add(autoOrderListBtn); btnPanel.add(receiveBtn); 

        inputPanel.add(fieldPanel);
        inputPanel.add(btnPanel);

        searchPanel.add(new JLabel("상품 검색:"));
        mSearchField = new JTextField(20);
        searchPanel.add(mSearchField);
        searchPanel.add(saveBtn); 

        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        panel.add(topPanel, BorderLayout.NORTH);

        String[] headers = {"상품명", "가격(₩)", "재고 수량", "발주 수량", "이익률(%)"}; 
        tableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { 
                return column == 3 || column == 4; 
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4) { return Integer.class; }
                return String.class;
            }
        };
        inventoryTable = new JTable(tableModel);
        panel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        
        mSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void changedUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            public void filter() { refreshTable(mSearchField.getText().trim()); }
        });
        
        autoOrderListBtn.addActionListener(e -> {
            boolean hasSold = false;
            int totalOrders = 0;
            for (Product p : productDB.values()) {
                if (p.soldQuantity > 0) {
                    p.orderedQuantity = p.soldQuantity; 
                    p.soldQuantity = 0; 
                    totalOrders += p.orderedQuantity;
                    hasSold = true;
                }
            }
            refreshTable(mSearchField.getText().trim());
            clearManagerFields();
            if (hasSold) {
                JOptionPane.showMessageDialog(this, "총 " + totalOrders + "개 상품에 대한 자동 발주 목록이 생성되었습니다.", "자동 발주 목록 생성 완료", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "판매된 상품이 없어 생성할 자동 발주 목록이 없습니다.");
            }
        });
        
        receiveBtn.addActionListener(e -> {
            int row = inventoryTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "발주를 승인할 상품을 선택해주세요."); return; }
            String name = (String) tableModel.getValueAt(row, 0);
            int orderedQtyOnTable;
            try { orderedQtyOnTable = Integer.parseInt(tableModel.getValueAt(row, 3).toString()); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "발주 수량이 유효한 숫자가 아닙니다."); return; }
            Product p = productDB.get(name);
            if (p == null || orderedQtyOnTable <= 0) { JOptionPane.showMessageDialog(this, name + "은(는) 발주 수량이 0입니다."); return; }
            int choice = JOptionPane.showConfirmDialog(this, "[발주 승인]\n상품명: " + name + "\n입고수량: " + orderedQtyOnTable + "개\n\n입고를 진행하시겠습니까?", "발주 승인 (입고)", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                p.quantity += orderedQtyOnTable; 
                p.orderedQuantity = 0; 
                refreshTable(mSearchField.getText().trim());
                clearManagerFields();
                JOptionPane.showMessageDialog(this, "발주 승인 완료! 재고에 " + orderedQtyOnTable + "개가 반영되었습니다.");
            }
        });

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int col = e.getColumn();
                    if (col != 3 && col != 4) return; 

                    int row = e.getFirstRow();
                    if (row < 0) return;
                    String name = (String) tableModel.getValueAt(row, 0);
                    Product p = productDB.get(name);
                    if (p == null) return;
                    
                    try {
                        int newValue = Integer.parseInt(tableModel.getValueAt(row, col).toString());
                        
                        if (col == 3) { 
                            if (newValue < 0) {
                                JOptionPane.showMessageDialog(SevenElevenManagement.this, "발주 수량은 0 이상이어야 합니다.");
                                tableModel.setValueAt(p.orderedQuantity, row, col);
                            } else {
                                p.orderedQuantity = newValue;
                            }
                        } else if (col == 4) { 
                            if (newValue < 0 || newValue > 100) {
                                JOptionPane.showMessageDialog(SevenElevenManagement.this, "이익률은 0에서 100 사이여야 합니다.");
                                tableModel.setValueAt(p.profitRate, row, col);
                            } else {
                                p.profitRate = newValue;
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(SevenElevenManagement.this, "해당 값은 숫자여야 합니다.");
                        if (col == 3) tableModel.setValueAt(p.orderedQuantity, row, col);
                        else if (col == 4) tableModel.setValueAt(p.profitRate, row, col);
                    }
                }
            }
        });
        
        addBtn.addActionListener(e -> {
            String name = mNameField.getText().trim();
            String priceStr = mPriceField.getText().trim();
            String qtyStr = mQtyField.getText().trim();
            String rateStr = mProfitRateField.getText().trim();
            
            if (name.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty() || rateStr.isEmpty()) { JOptionPane.showMessageDialog(this, "모든 필드를 채워주세요."); return; }
            if (productDB.containsKey(name)) { JOptionPane.showMessageDialog(this, "이미 존재하는 상품입니다."); return; }
            try {
                int price = Integer.parseInt(priceStr);
                int qty = Integer.parseInt(qtyStr); 
                int rate = Integer.parseInt(rateStr);
                if (price <= 0 || qty < 0 || rate < 0 || rate > 100) { JOptionPane.showMessageDialog(this, "가격은 0보다 커야하며, 수량은 0이상, 이익률은 0~100 사이여야 합니다."); return; }
                
                productDB.put(name, new Product(name, price, qty, 0, 0, rate));
                refreshTable(mSearchField.getText().trim()); 
                clearManagerFields();
                JOptionPane.showMessageDialog(this, "상품 등록 완료!");
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "필드 값은 숫자여야 합니다."); }
        });
        
        editBtn.addActionListener(e -> {
            int row = inventoryTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "수정할 상품을 선택해주세요."); return; }
            String name = (String) tableModel.getValueAt(row, 0); 
            String newPriceStr = mPriceField.getText().trim();
            String newRateStr = mProfitRateField.getText().trim();

            if (newPriceStr.isEmpty() && newRateStr.isEmpty()) { JOptionPane.showMessageDialog(this, "변경할 가격이나 이익률을 입력해주세요."); return; }

            Product p = productDB.get(name);
            boolean modified = false;

            try {
                if (!newPriceStr.isEmpty()) {
                    int newPrice = Integer.parseInt(newPriceStr);
                    if (newPrice <= 0) { JOptionPane.showMessageDialog(this, "가격은 0보다 커야 합니다."); return; }
                    p.price = newPrice; 
                    modified = true;
                }
                if (!newRateStr.isEmpty()) {
                    int newRate = Integer.parseInt(newRateStr);
                    if (newRate < 0 || newRate > 100) { JOptionPane.showMessageDialog(this, "이익률은 0~100 사이여야 합니다."); return; }
                    p.profitRate = newRate; 
                    modified = true;
                }
                
                if (modified) {
                    refreshTable(mSearchField.getText().trim()); 
                    JOptionPane.showMessageDialog(this, "정보가 수정되었습니다.");
                    clearManagerFields();
                }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "가격과 이익률은 숫자여야 합니다."); }
        });
        
        delBtn.addActionListener(e -> {
            int row = inventoryTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "삭제할 상품을 테이블에서 선택해주세요."); return; }
            String name = (String) tableModel.getValueAt(row, 0);
            productDB.remove(name);
            refreshTable(mSearchField.getText().trim()); 
            JOptionPane.showMessageDialog(this, "삭제되었습니다.");
        });
        
        saveBtn.addActionListener(e -> {
            if (saveProductsToDB() && saveEventsToDB() && saveRevenueToDB()) { 
                JOptionPane.showMessageDialog(this, "모든 데이터가 성공적으로 DB에 저장되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "데이터 저장에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        inventoryTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = inventoryTable.getSelectedRow();
                if (row != -1) {
                    String name = (String) tableModel.getValueAt(row, 0);
                    Product p = productDB.get(name);
                    if (p != null) { 
                        mNameField.setText(p.name);
                        mPriceField.setText(String.valueOf(p.price));
                        mProfitRateField.setText(String.valueOf(p.profitRate)); 
                        mQtyField.setText("");
                    }
                }
            }
        });

        return panel;
    }
    
    private JPanel createEventPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        
        JTextField eventNameField = new JTextField(15);
        JComboBox<String> eventTypeCombo = new JComboBox<>(EventType.getValues());
        JTextField targetProductField = new JTextField(15);
        JTextField valueField = new JTextField(15); 
        
        JButton addEventBtn = new JButton("이벤트 등록");
        JButton delEventBtn = new JButton("이벤트 삭제 (이름으로)");
        
        inputPanel.add(new JLabel("이벤트명:"));
        inputPanel.add(eventNameField);
        inputPanel.add(new JLabel("유형 (1+1, 2+1, 할인, 묶음):"));
        inputPanel.add(eventTypeCombo);
        inputPanel.add(new JLabel("대상 상품명:"));
        inputPanel.add(targetProductField);
        inputPanel.add(new JLabel("값 (할인금액/N+1 수량/묶음가격):"));
        inputPanel.add(valueField);
        inputPanel.add(new JLabel("")); 
        inputPanel.add(new JLabel("")); 
        inputPanel.add(addEventBtn);
        inputPanel.add(delEventBtn);
        
        panel.add(inputPanel, BorderLayout.NORTH);

        String[] headers = {"이벤트명", "유형", "대상 상품", "값"};
        DefaultTableModel eventTableModel = new DefaultTableModel(headers, 0);
        JTable eventTable = new JTable(eventTableModel);
        panel.add(new JScrollPane(eventTable), BorderLayout.CENTER);

        Runnable refreshEventTable = () -> {
            eventTableModel.setRowCount(0);
            for (EventRule rule : eventDB.values()) {
                eventTableModel.addRow(new Object[]{rule.ruleName, rule.type, rule.targetProduct, rule.value});
            }
        };
        
        addEventBtn.addActionListener(e -> {
            String name = eventNameField.getText().trim();
            String type = (String) eventTypeCombo.getSelectedItem(); 
            String target = targetProductField.getText().trim();
            String valueStr = valueField.getText().trim();

            if (name.isEmpty() || target.isEmpty() || valueStr.isEmpty()) { JOptionPane.showMessageDialog(this, "모든 필드를 채워주세요."); return; }
            if (!productDB.containsKey(target)) { JOptionPane.showMessageDialog(this, "대상 상품이 재고 목록에 없습니다."); return; }

            try {
                int value = Integer.parseInt(valueStr);
                
                if (value <= 0 && (type.equals(EventType.ONE_PLUS_ONE) || type.equals(EventType.TWO_PLUS_ONE) || type.equals(EventType.BUNDLE))) {
                    JOptionPane.showMessageDialog(this, "1+1, 2+1 및 묶음 상품의 값은 0보다 커야 합니다."); return;
                }
                
                EventRule newRule = new EventRule(name, type, target, value);
                eventDB.put(name, newRule);
                refreshEventTable.run();
                JOptionPane.showMessageDialog(this, name + " 이벤트 등록 완료.");
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "값은 숫자여야 합니다."); }
        });
        
        delEventBtn.addActionListener(e -> {
            String name = eventNameField.getText().trim();
            if (eventDB.containsKey(name)) {
                eventDB.remove(name);
                refreshEventTable.run();
                JOptionPane.showMessageDialog(this, name + " 이벤트가 삭제되었습니다.");
            } else { JOptionPane.showMessageDialog(this, "해당 이름의 이벤트가 없습니다."); }
        });

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("이벤트 관리")) {
                    refreshEventTable.run();
                }
            }
        });
        
        refreshEventTable.run();
        return panel;
    }

    private JPanel createSalesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); 
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("상품 재고 목록 (클릭하여 장바구니 추가)"));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("상품 검색:"));
        sSearchField = new JTextField(20);
        searchPanel.add(sSearchField);
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        
        String[] headers = {"상품명", "가격(₩)", "남은 재고"};
        salesInventoryTableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        salesInventoryTable = new JTable(salesInventoryTableModel);
        leftPanel.add(new JScrollPane(salesInventoryTable), BorderLayout.CENTER);

        sSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void changedUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            public void filter() { refreshSalesInventoryTable(sSearchField.getText().trim()); }
        });

        salesInventoryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = salesInventoryTable.getSelectedRow();
                    if (row != -1) {
                        String name = (String) salesInventoryTableModel.getValueAt(row, 0);
                        Product p = productDB.get(name);
                        
                        if (p == null || p.quantity <= 0) {
                            JOptionPane.showMessageDialog(SevenElevenManagement.this, (p == null ? "상품이 존재하지 않거나" : p.name) + "은(는) 재고가 부족합니다.");
                            return;
                        }

                        int currentQty = currentCart.getOrDefault(name, 0);
                        
                        if (currentQty < p.quantity) {
                             currentCart.put(name, currentQty + 1);
                             updateCartTable();
                             updateTotal();
                        } else {
                            JOptionPane.showMessageDialog(SevenElevenManagement.this, p.name + "의 판매 가능 재고(" + p.quantity + "개)를 초과하여 담을 수 없습니다.");
                        }
                    }
                }
            }
        });
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("장바구니 및 결제"));

        String[] cartHeaders = {"상품명", "가격(₩)", "수량", "합계"};
        cartTableModel = new CartTableModel(cartHeaders);
        cartTable = new JTable(cartTableModel);
        rightPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        cartTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 2) {
                    int row = e.getFirstRow();
                    if (row == -1) return;
                    String name = (String) cartTableModel.getValueAt(row, 0);
                    Product p = productDB.get(name);
                    
                    if (p == null) { updateCartTable(); updateTotal(); return; }

                    try {
                        int newQty = Integer.parseInt(cartTableModel.getValueAt(row, 2).toString());
                        if (newQty <= 0) {
                            currentCart.remove(name);
                            updateCartTable();
                            JOptionPane.showMessageDialog(SevenElevenManagement.this, name + "이(가) 장바구니에서 삭제되었습니다.");
                        } else if (newQty > p.quantity) {
                            JOptionPane.showMessageDialog(SevenElevenManagement.this, p.name + "의 재고가 부족합니다! (현재 재고: " + p.quantity + "개)");
                            Integer oldQty = currentCart.get(name);
                            currentCart.put(name, oldQty != null ? oldQty : 1); 
                            updateCartTable(); 
                        } else {
                            currentCart.put(name, newQty);
                        }
                        updateTotal();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(SevenElevenManagement.this, "수량은 정확한 숫자여야 합니다.");
                        Integer oldQty = currentCart.get(name);
                        currentCart.put(name, oldQty != null ? oldQty : 1); 
                        updateCartTable(); 
                    }
                }
            }
        });

        JPanel cancelBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton removeSelectedBtn = new JButton("선택 상품 취소");
        removeSelectedBtn.setBackground(new Color(255, 180, 0));
        removeSelectedBtn.setForeground(Color.BLACK);
        cancelBtnPanel.add(removeSelectedBtn);

        removeSelectedBtn.addActionListener(e -> {
            int row = cartTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "장바구니에서 취소할 상품을 선택해주세요."); return; }
            String name = (String) cartTableModel.getValueAt(row, 0);
            currentCart.remove(name);
            updateCartTable();
            updateTotal();
            JOptionPane.showMessageDialog(this, name + "이(가) 장바구니에서 취소되었습니다.");
        });

        rightPanel.add(cancelBtnPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        totalLabel = new JLabel("총 결제 금액 (이벤트 적용 후): ₩0 (할인: ₩0)", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 10));

        JPanel discountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        discountPanel.add(new JLabel("총 수동 할인 (₩):"));
        manualDiscountField = new JTextField("0", 10);
        discountPanel.add(manualDiscountField);
        
        manualDiscountField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void changedUpdate(DocumentEvent e) { updateTotal(); }
            @Override public void removeUpdate(DocumentEvent e) { updateTotal(); }
            @Override public void insertUpdate(DocumentEvent e) { updateTotal(); }
        });
        
        JButton sellBtn = new JButton("결제 완료");
        sellBtn.setBackground(new Color(255, 140, 0));
        sellBtn.setForeground(Color.WHITE);
        sellBtn.setPreferredSize(new Dimension(150, 50));
        
        JButton cancelAllBtn = new JButton("전체 취소");
        cancelAllBtn.setBackground(new Color(150, 150, 150));
        cancelAllBtn.setForeground(Color.WHITE);
        cancelAllBtn.setPreferredSize(new Dimension(150, 50));
        
        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnWrapper.add(cancelAllBtn);
        btnWrapper.add(sellBtn);

        JPanel topBottomPanel = new JPanel(new BorderLayout());
        topBottomPanel.add(totalLabel, BorderLayout.NORTH);
        topBottomPanel.add(discountPanel, BorderLayout.SOUTH);
        
        bottomPanel.add(topBottomPanel, BorderLayout.CENTER);
        bottomPanel.add(btnWrapper, BorderLayout.EAST);
        
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        receiptArea = new JTextArea(8, 0);
        receiptArea.setEditable(false);
        receiptArea.setText("--- 판매 기록 로그 ---\n");
        
        revenueProfitLabel = new JLabel(); 
        revenueProfitLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        revenueProfitLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JPanel logAndStatusPanel = new JPanel(new BorderLayout());
        logAndStatusPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);
        logAndStatusPanel.add(revenueProfitLabel, BorderLayout.NORTH); 

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.5);

        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(logAndStatusPanel, BorderLayout.SOUTH);

        updateRevenueProfitLabelInSalesTab();

        sellBtn.addActionListener(e -> {
            if (currentCart.isEmpty()) { JOptionPane.showMessageDialog(this, "장바구니가 비어있습니다. 상품을 추가해주세요."); return; }
            
            long finalTotal = calculateFinalTotal();
            long manualDiscount = getManualDiscount();
            
            int confirm = JOptionPane.showConfirmDialog(this, "최종 금액 ₩" + currencyFormat.format(finalTotal).substring(1) + "원 결제를 진행하시겠습니까?", "결제 확인", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                
                Map<String, TransactionDetail> transactionDetails = calculateNetProfitAndApplySale(finalTotal, manualDiscount);
                
                long netProfit = transactionDetails.get("::TOTAL_PROFIT::").itemNetProfit;
                long totalGrossRevenue = calculateGrossRevenue();
                long totalWithEvents = calculateTotalWithEvents();
                long totalDiscount = (totalGrossRevenue - totalWithEvents) + manualDiscount;
                
                totalRevenue += finalTotal; 

                StringBuilder log = new StringBuilder(String.format("--- [거래 완료] 최종액: ₩%,d (순 수익: ₩%,d) ---\n", finalTotal, netProfit));
                for(TransactionDetail detail : transactionDetails.values()) {
                    if (detail.name != null) {
                         String detailLine = String.format("  - %s | ₩%,d x %d개", detail.name, detail.unitPrice, detail.quantity);
                         if (detail.freeCount > 0) {
                             detailLine += String.format(" (N+1 적용, %d개 공짜)", detail.freeCount);
                         } else if (detail.itemFinalPrice < (long)detail.unitPrice * detail.quantity) {
                             if (detail.freeCount == 0 && detail.itemFinalPrice < (long)detail.unitPrice * detail.quantity) {
                                  long itemDiscount = ((long)detail.unitPrice * detail.quantity) - detail.itemFinalPrice;
                                  detailLine += String.format(" (할인 적용, ₩%,d 할인)", itemDiscount);
                             }
                         }
                         log.append(detailLine).append("\n");
                    }
                }
                log.append(String.format("---------------------------\n[최종 상세]\n매출 (할인 후): ₩%,d\n총 할인: ₩%,d\n", 
                                         finalTotal, totalDiscount));
                receiptArea.append(log.toString());
                receiptArea.append("---------------------------\n");

                currentCart.clear();
                manualDiscountField.setText("0"); 
                updateCartTable();
                updateTotal();
                refreshSalesInventoryTable(sSearchField.getText().trim());
                updateRevenueProfitLabelInSalesTab(); 
                
                JOptionPane.showMessageDialog(this, 
                    "결제 완료! (최종 매출: " + currencyFormat.format(finalTotal).substring(1) + "원, 순 수익: " + currencyFormat.format(netProfit).substring(1) + "원)", 
                    "결제 완료", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cancelAllBtn.addActionListener(e -> {
            if (currentCart.isEmpty()) { JOptionPane.showMessageDialog(this, "장바구니가 이미 비어있습니다."); return; }
            int confirm = JOptionPane.showConfirmDialog(this, "장바구니의 모든 상품을 취소하시겠습니까?", "전체 취소", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                currentCart.clear();
                manualDiscountField.setText("0"); 
                updateCartTable();
                updateTotal();
                JOptionPane.showMessageDialog(this, "전체 취소되었습니다.");
            }
        });

        refreshSalesInventoryTable("");
        updateCartTable();

        return panel;
    }
    
    private JPanel createRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("매출 및 상품 판매 현황"));

        JPanel summaryPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        
        revenueSummaryLabel = new JLabel("총 매출: ₩0, 총 수익: ₩0"); 
        revenueSummaryLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        revenueSummaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton refreshBtn = new JButton("현황 새로고침");
        refreshBtn.addActionListener(e -> updateRevenuePanel());
        
        summaryPanel.add(revenueSummaryLabel);
        summaryPanel.add(refreshBtn);
        
        panel.add(summaryPanel, BorderLayout.NORTH);

        String[] headers = {"상품명", "가격(₩)", "총 판매량", "총 발주량", "총 예상 수익(이익률 기반)"};
        revenueTableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                 if (columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4) { return Long.class; }
                 return String.class;
            }
        };
        revenueTable = new JTable(revenueTableModel);
        
        panel.add(new JScrollPane(revenueTable), BorderLayout.CENTER);
        
        return panel;
    }

    private void updateRevenueProfitLabelInSalesTab() {
        long totalExpectedProfit = 0;
        for (Product p : productDB.values()) {
            totalExpectedProfit += (long)(p.price * p.soldQuantity * (p.profitRate / 100.0));
        }

        revenueProfitLabel.setText(String.format(
            "💰 누적 총 매출: ₩%s, 누적 총 수익(예상): ₩%s",
            currencyFormat.format(totalRevenue).substring(1),
            currencyFormat.format(totalExpectedProfit).substring(1)
        ));
    }
    
    private void updateRevenuePanel() {
        long totalCalculatedExpectedProfit = 0;
        
        revenueTableModel.setRowCount(0);
        for (Product p : productDB.values()) {
            long estimatedGrossProfit = (long)(p.price * p.soldQuantity * (p.profitRate / 100.0));
            totalCalculatedExpectedProfit += estimatedGrossProfit;
            
            revenueTableModel.addRow(new Object[]{
                p.name, 
                (long)p.price, 
                (long)p.soldQuantity, 
                (long)p.orderedQuantity, 
                estimatedGrossProfit 
            });
        }
        
        revenueSummaryLabel.setText(String.format(
            "총 매출: ₩%s, 총 수익(예상): ₩%s",
            currencyFormat.format(totalRevenue).substring(1),
            currencyFormat.format(totalCalculatedExpectedProfit).substring(1)
        ));
    }
    
    private SaleResult calculateItemPrice(String name, int qty) {
        Product p = productDB.get(name);
        if (p == null) return new SaleResult(0, 0);

        long itemTotalPrice = (long)p.price * qty;
        int freeCount = 0;
        
        for (EventRule rule : eventDB.values()) {
            if (rule.targetProduct.equals(name)) {
                if (rule.type.equals(EventType.ONE_PLUS_ONE)) {
                    freeCount = qty / 2;
                    int paidCount = qty - freeCount;
                    itemTotalPrice = (long) paidCount * p.price;
                    return new SaleResult(itemTotalPrice, freeCount);
                } else if (rule.type.equals(EventType.TWO_PLUS_ONE)) {
                    freeCount = qty / 3;
                    int paidCount = qty - freeCount;
                    itemTotalPrice = (long) paidCount * p.price;
                    return new SaleResult(itemTotalPrice, freeCount);
                } else if (rule.type.equals(EventType.DISCOUNT)) {
                    int discountAmount = rule.value;
                    itemTotalPrice = (long) qty * (p.price - discountAmount);
                    itemTotalPrice = Math.max(0, itemTotalPrice); 
                    return new SaleResult(itemTotalPrice, 0);
                } else if (rule.type.equals(EventType.BUNDLE)) {
                    int bundleQty = rule.value;
                    int bundles = qty / bundleQty; 
                    int remaining = qty % bundleQty;
                    itemTotalPrice = (long)(bundles * p.price) + (remaining * p.price); 
                    return new SaleResult(itemTotalPrice, 0); 
                }
                break;
            }
        }
        
        return new SaleResult(itemTotalPrice, 0); 
    }
    
    private long calculateTotalWithEvents() {
        long total = 0;
        for (String name : currentCart.keySet()) {
            int qty = currentCart.get(name);
            SaleResult result = calculateItemPrice(name, qty);
            total += result.totalPrice;
        }
        return total;
    }
    
    private long calculateGrossRevenue() {
        long gross = 0;
        for (String name : currentCart.keySet()) {
            Product p = productDB.get(name);
            if (p != null) {
                gross += (long) p.price * currentCart.get(name);
            }
        }
        return gross;
    }
    
    private long getManualDiscount() {
        try {
            String discStr = manualDiscountField.getText().trim();
            return discStr.isEmpty() ? 0 : Long.parseLong(discStr);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private long calculateFinalTotal() {
        long totalWithEvents = calculateTotalWithEvents();
        long manualDiscount = getManualDiscount();
        return Math.max(0, totalWithEvents - manualDiscount);
    }
    
    private void updateTotal() {
        long totalWithEvents = calculateTotalWithEvents();
        long manualDiscount = getManualDiscount();
        long finalTotal = calculateFinalTotal();
        
        long totalDiscount = (calculateGrossRevenue() - totalWithEvents) + manualDiscount; 
        
        totalLabel.setText(String.format("총 결제 금액 (이벤트 적용 후): ₩%,d (총 할인: ₩%,d)", finalTotal, totalDiscount));
    }
    
    private Map<String, TransactionDetail> calculateNetProfitAndApplySale(long totalFinalPrice, long manualDiscount) {
        Map<String, TransactionDetail> details = new HashMap<>();
        long totalCalculatedProfit = 0;
        
        for (String name : currentCart.keySet()) {
            int qty = currentCart.get(name);
            Product p = productDB.get(name);
            if (p == null) continue;

            SaleResult result = calculateItemPrice(name, qty);
            long itemTotalPrice = result.totalPrice;

            p.quantity -= qty;
            p.soldQuantity += qty; 
            
            long itemNetProfit = p.calculateProfit(itemTotalPrice);
            totalCalculatedProfit += itemNetProfit;
            
            details.put(name, new TransactionDetail(name, p.price, qty, itemTotalPrice, itemNetProfit, result.freeCount));
        }

        long finalNetProfit = Math.max(0, totalCalculatedProfit - manualDiscount);
        
        details.put("::TOTAL_PROFIT::", new TransactionDetail(finalNetProfit));
        return details;
    }

    private void refreshTable(String filter) {
        tableModel.setRowCount(0);
        String lowerCaseFilter = filter.toLowerCase();
        
        for (String key : productDB.keySet()) {
            Product p = productDB.get(key);
            
            if (p.name.toLowerCase().contains(lowerCaseFilter)) {
                tableModel.addRow(new Object[]{p.name, p.price, p.quantity, p.orderedQuantity, p.profitRate});
            }
        }
    }

    private void refreshSalesInventoryTable(String filter) {
        salesInventoryTableModel.setRowCount(0);
        String lowerCaseFilter = filter.toLowerCase();
        
        for (String key : productDB.keySet()) {
            Product p = productDB.get(key);
            
            if (p.name.toLowerCase().contains(lowerCaseFilter)) {
                salesInventoryTableModel.addRow(new Object[]{p.name, p.price, p.quantity});
            }
        }
    }

    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        for (String name : currentCart.keySet()) {
            int qty = currentCart.get(name);
            Product p = productDB.get(name);
            if (p != null) {
                // 수량 변경 시 합계는 Gross Price로 보여줍니다. (실제 이벤트 적용은 totalLabel에서 반영)
                cartTableModel.addRow(new Object[]{name, p.price, qty, (long)p.price * qty}); 
            }
        }
    }

    private void clearManagerFields() {
        mNameField.setText("");
        mPriceField.setText("");
        mQtyField.setText("");
        mProfitRateField.setText("");
    }
    
    private class CartTableModel extends DefaultTableModel {
        public CartTableModel(String[] headers) { super(headers, 0); }
        @Override
        public boolean isCellEditable(int row, int column) { return column == 2; }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 1 || columnIndex == 2 || columnIndex == 3) { return Long.class; }
            return String.class;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SevenElevenManagement().setVisible(true));
    }
}