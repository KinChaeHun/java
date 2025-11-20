import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SimplePaint extends JFrame {
    
    private int startX, startY;
    private String currentTool = "Brush";
    private Color currentColor = Color.BLACK;
    private int strokeSize = 2;
    private boolean isFilled = false;
    
    private BufferedImage clipboardImage = null; 

    private DrawPanel drawPanel;
    private JLabel statusLabel;

    public SimplePaint() {
        setTitle("자바 그림판 (Java Paint) ver-1.1");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        drawPanel = new DrawPanel(this); 
        
        createMenuBar();
        createToolBar();

        add(drawPanel, BorderLayout.CENTER);

        statusLabel = new JLabel(" 도구: 브러시 | 색상: 검정");
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public BufferedImage getClipboardImage() { return clipboardImage; }
    public void setClipboardImage(BufferedImage img) { clipboardImage = img; }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("파일");
        JMenuItem newItem = new JMenuItem("새로 만들기");
        newItem.addActionListener(e -> { drawPanel.applySelectedImageToCanvas(); drawPanel.clear(); });
        JMenuItem openItem = new JMenuItem("열기");
        openItem.addActionListener(e -> { drawPanel.applySelectedImageToCanvas(); openImage(); });
        JMenuItem saveItem = new JMenuItem("저장");
        saveItem.addActionListener(e -> { drawPanel.applySelectedImageToCanvas(); saveImageAs(); });
        JMenuItem saveAsItem = new JMenuItem("다른 이름으로 저장");
        saveAsItem.addActionListener(e -> { drawPanel.applySelectedImageToCanvas(); saveImageAs(); });
        JMenuItem exitItem = new JMenuItem("종료");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(newItem); fileMenu.add(openItem); fileMenu.add(saveItem); fileMenu.add(saveAsItem); fileMenu.addSeparator(); fileMenu.add(exitItem);

        JMenu editMenu = new JMenu("편집");
        JMenuItem cutItem = new JMenuItem("잘라내기");
        cutItem.addActionListener(e -> drawPanel.cutSelection());
        // 단축키 힌트 추가
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        
        JMenuItem copyItem = new JMenuItem("복사");
        copyItem.addActionListener(e -> drawPanel.copySelection());
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        
        JMenuItem pasteItem = new JMenuItem("붙여넣기");
        pasteItem.addActionListener(e -> drawPanel.pasteSelection());
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));

        editMenu.add(cutItem); editMenu.add(copyItem); editMenu.add(pasteItem);

        JMenu helpMenu = new JMenu("정보");
        JMenuItem infoItem = new JMenuItem("정보");
        infoItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "그림판 ver-1.1 | 제작자 : 김채훈"));
        helpMenu.add(infoItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, 
                          Color.BLUE, new Color(0, 0, 128), new Color(128, 0, 128), Color.BLACK};
        String[] colorNames = {"빨강", "주황", "노랑", "초록", "파랑", "남색", "보라", "검정"};

        toolBar.add(new JLabel("색상: "));
        for (int i = 0; i < colors.length; i++) {
            JButton btn = new JButton(" ");
            btn.setBackground(colors[i]);
            btn.setPreferredSize(new Dimension(20, 20));
            final Color c = colors[i];
            final String name = colorNames[i];
            btn.addActionListener(e -> {
                drawPanel.applySelectedImageToCanvas(); 
                currentColor = c;
                currentTool = "Brush";
                statusLabel.setText(" 도구: 브러시 | 색상: " + name);
            });
            toolBar.add(btn);
        }
        
        JButton colorChooserBtn = new JButton("🎨");
        colorChooserBtn.setToolTipText("색상 지정");

        colorChooserBtn.addActionListener(e -> {
            drawPanel.applySelectedImageToCanvas(); 
            Color selectedColor = JColorChooser.showDialog(this, "색상 선택", currentColor);
            if (selectedColor != null) {
                currentColor = selectedColor;
                currentTool = "Brush";
                String hexColor = String.format("#%02x%02x%02x", 
                                                currentColor.getRed(), 
                                                currentColor.getGreen(), 
                                                currentColor.getBlue());
                statusLabel.setText(" 도구: 브러시 | 색상: " + hexColor);
            }
        });
        toolBar.add(colorChooserBtn);

        toolBar.addSeparator();

        String[] tools = {"브러시", "지우개", "선", "네모", "원", "영역선택"};
        String[] toolCmds = {"Brush", "Eraser", "Line", "Rect", "Oval", "Select"};

        for (int i = 0; i < tools.length; i++) {
            JButton btn = new JButton(tools[i]);
            final String cmd = toolCmds[i];
            final int index = i;
            btn.addActionListener(e -> {
                drawPanel.applySelectedImageToCanvas(); 
                currentTool = cmd;
                statusLabel.setText(" 도구: " + tools[index]);
            });
            toolBar.add(btn);
        }

        toolBar.addSeparator();

        JCheckBox fillCheckBox = new JCheckBox("채우기");
        fillCheckBox.addActionListener(e -> isFilled = fillCheckBox.isSelected());
        toolBar.add(fillCheckBox);

        toolBar.add(new JLabel(" 크기: "));
        JSlider sizeSlider = new JSlider(1, 20, 2);
        sizeSlider.addChangeListener(e -> strokeSize = sizeSlider.getValue());
        toolBar.add(sizeSlider);

        add(toolBar, BorderLayout.NORTH);
    }

    private void openImage() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "png", "jpg", "jpeg");
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage img = ImageIO.read(chooser.getSelectedFile());
                drawPanel.loadImage(img);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "이미지 로드 실패: " + ex.getMessage());
            }
        }
    }

    private void saveImageAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("drawing.png"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                ImageIO.write(drawPanel.getImage(), "png", chooser.getSelectedFile());
                JOptionPane.showMessageDialog(this, "저장되었습니다.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "저장 실패: " + ex.getMessage());
            }
        }
    }

    class DrawPanel extends JPanel {
        private BufferedImage canvasImage;
        private Graphics2D g2d;
        private int endX, endY;
        
        private SimplePaint parentFrame;
        private boolean isSelectionActive = false; 
        private Rectangle selectionBounds = null; 
        private BufferedImage selectedImage = null; 
        private boolean isSelecting = false; 
        private int lastX, lastY; 

        public DrawPanel(SimplePaint parent) {
            this.parentFrame = parent;
            setBackground(Color.WHITE);
            
            // --- 키보드 액션 설정 (Ctrl+C, Ctrl+V, Backspace) ---
            setKeyBindings();
            // ----------------------------------------------------
            
            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (isSelectionActive && selectionBounds != null && selectionBounds.contains(e.getPoint())) {
                        lastX = e.getX();
                        lastY = e.getY();
                        return; 
                    }
                    
                    applySelectedImageToCanvas(); 
                    
                    startX = e.getX();
                    startY = e.getY();
                    endX = e.getX();
                    endY = e.getY();
                    
                    if (currentTool.equals("Select")) {
                        isSelecting = true;
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (isSelectionActive && selectionBounds != null) {
                        int dx = e.getX() - lastX;
                        int dy = e.getY() - lastY;
                        selectionBounds.x += dx;
                        selectionBounds.y += dy;
                        lastX = e.getX();
                        lastY = e.getY();
                        repaint();
                        return;
                    }
                    
                    endX = e.getX();
                    endY = e.getY();

                    if (currentTool.equals("Brush")) {
                        drawBrush(startX, startY, endX, endY);
                        startX = endX;
                        startY = endY;
                        repaint();
                    } else if (currentTool.equals("Eraser")) {
                        drawEraser(startX, startY, endX, endY);
                        startX = endX;
                        startY = endY;
                        repaint();
                    } else if (currentTool.equals("Line") || currentTool.equals("Rect") || currentTool.equals("Oval") || currentTool.equals("Select")) {
                        repaint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (isSelectionActive) {
                         return; 
                    }
                    
                    endX = e.getX();
                    endY = e.getY();
                    
                    if (currentTool.equals("Line") || currentTool.equals("Rect") || currentTool.equals("Oval")) {
                        drawShape(g2d, currentTool, startX, startY, endX, endY, currentColor, isFilled);
                        repaint();
                    } else if (currentTool.equals("Select")) {
                        int x = Math.min(startX, endX);
                        int y = Math.min(startY, endY);
                        int w = Math.abs(endX - startX);
                        int h = Math.abs(endY - startY);
                        
                        if (w > 0 && h > 0) {
                            selectionBounds = new Rectangle(x, y, w, h);
                            selectedImage = null; 
                        }
                        isSelecting = false;
                        repaint();
                    }
                }
            };

            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
            
            // 포커스를 받을 수 있도록 설정
            setFocusable(true); 
        }
        
        // --- 키 바인딩 설정 메서드 ---
        private void setKeyBindings() {
            InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = this.getActionMap();

            // Ctrl+C (복사) 바인딩
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), "copyAction");
            actionMap.put("copyAction", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    copySelection();
                }
            });

            // Ctrl+V (붙여넣기) 바인딩
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), "pasteAction");
            actionMap.put("pasteAction", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pasteSelection();
                }
            });
            
            // Backspace (선택 영역 지우기) 바인딩
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "clearSelectionAction");
            actionMap.put("clearSelectionAction", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearSelectionArea();
                }
            });
        }
        // -----------------------------
        
        private BufferedImage deepCopy(BufferedImage bi) {
            if (bi == null) return null;
            BufferedImage newImage = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
            Graphics2D g = newImage.createGraphics();
            g.drawImage(bi, 0, 0, null);
            g.dispose();
            return newImage;
        }

        // --- 편집 메뉴 로직 ---
        public void copySelection() {
            // 떠있는 이미지 확정 (이동 중일 경우)
            applySelectedImageToCanvas(); 
            
            if (selectionBounds != null && selectionBounds.width > 0 && selectionBounds.height > 0) {
                try {
                    BufferedImage captured = canvasImage.getSubimage(selectionBounds.x, selectionBounds.y, selectionBounds.width, selectionBounds.height);
                    parentFrame.setClipboardImage(deepCopy(captured));
                    JOptionPane.showMessageDialog(this, "영역이 클립보드에 복사되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                } catch (RasterFormatException ex) {
                    JOptionPane.showMessageDialog(this, "선택 영역이 캔버스 범위를 벗어납니다.", "경고", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "활성화된 선택 영역이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        public void cutSelection() {
            // 떠있는 이미지 확정
            applySelectedImageToCanvas(); 
            
            if (selectionBounds != null && selectionBounds.width > 0 && selectionBounds.height > 0) {
                copySelection(); // 복사 로직 재활용
                
                // 원본 캔버스 영역 지우기
                g2d.setColor(Color.WHITE);
                g2d.fillRect(selectionBounds.x, selectionBounds.y, selectionBounds.width, selectionBounds.height);
                
                // 선택 상태는 유지 (점선은 남김)
                selectedImage = null; 
                repaint();

                JOptionPane.showMessageDialog(this, "선택 영역이 잘라내기 되었습니다. (점선 유지)", "알림", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "활성화된 선택 영역이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        public void pasteSelection() {
            BufferedImage clipboard = parentFrame.getClipboardImage();
            if (clipboard != null) {
                applySelectedImageToCanvas(); 

                selectedImage = deepCopy(clipboard);
                
                int w = selectedImage.getWidth();
                int h = selectedImage.getHeight();
                
                int x, y;
                if (selectionBounds != null) {
                    x = selectionBounds.x + (selectionBounds.width - w) / 2;
                    y = selectionBounds.y + (selectionBounds.height - h) / 2;
                } else {
                    x = (getWidth() - w) / 2;
                    y = (getHeight() - h) / 2;
                }
                
                selectionBounds = new Rectangle(x, y, w, h);
                isSelectionActive = true;
                
                // 붙여넣기 후에는 도구를 Select로 변경하여 이동 상태임을 사용자에게 알림
                currentTool = "Select";
                parentFrame.statusLabel.setText(" 도구: 영역선택 | 색상: N/A");
                
                repaint();
                JOptionPane.showMessageDialog(this, "클립보드 내용이 붙여넣기 되었습니다.\n마우스를 이용해 이동 후, 다른 도구를 선택해 확정하세요.", "안내", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "클립보드에 붙여넣을 이미지가 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        // --- Backspace로 선택 영역 지우기 ---
        public void clearSelectionArea() {
            // 떠있는 이미지 확정
            applySelectedImageToCanvas(); 
            
            if (selectionBounds != null && selectionBounds.width > 0 && selectionBounds.height > 0) {
                // 원본 캔버스 영역 지우기
                g2d.setColor(Color.WHITE);
                g2d.fillRect(selectionBounds.x, selectionBounds.y, selectionBounds.width, selectionBounds.height);
                
                // 선택 영역 정보 초기화
                selectionBounds = null;
                repaint();

                JOptionPane.showMessageDialog(this, "선택 영역이 지워졌습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "지울 선택 영역이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        }
        // ------------------------------------
        
        public void applySelectedImageToCanvas() {
            if (isSelectionActive && selectedImage != null && selectionBounds != null) {
                g2d.drawImage(selectedImage, selectionBounds.x, selectionBounds.y, null);
            }
            isSelectionActive = false;
            selectedImage = null;
            
            if (!currentTool.equals("Select")) selectionBounds = null;
            
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (canvasImage == null) {
                canvasImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                g2d = canvasImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setPaint(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
            g.drawImage(canvasImage, 0, 0, null);

            if (isSelectionActive && selectedImage != null && selectionBounds != null) {
                g.drawImage(selectedImage, selectionBounds.x, selectionBounds.y, null);
            }
            
            // 점선 테두리 그리기
            Graphics2D gTemp = (Graphics2D) g.create();
            float dash[] = {5.0f};
            gTemp.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
            gTemp.setColor(Color.BLACK);
            
            if (currentTool.equals("Select") && isSelecting) {
                int x = Math.min(startX, endX);
                int y = Math.min(startY, endY);
                int w = Math.abs(endX - startX);
                int h = Math.abs(endY - startY);
                gTemp.drawRect(x, y, w, h);
            } 
            else if (selectionBounds != null) {
                gTemp.drawRect(selectionBounds.x, selectionBounds.y, selectionBounds.width, selectionBounds.height);
            }
            gTemp.dispose();

            // 일반 도형 드래그 미리보기
            if (currentTool.equals("Line") || currentTool.equals("Rect") || currentTool.equals("Oval")) {
                if (startX != endX || startY != endY) { 
                    gTemp = (Graphics2D) g.create();
                    gTemp.setColor(currentColor);
                    gTemp.setStroke(new BasicStroke(strokeSize));
                    
                    drawShape(gTemp, currentTool, startX, startY, endX, endY, currentColor, isFilled);
                    gTemp.dispose();
                }
            }
        }

        public void clear() {
            g2d.setPaint(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            repaint();
        }

        public void loadImage(BufferedImage img) {
            canvasImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            g2d = canvasImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(img, 0, 0, getWidth(), getHeight(), null);
            repaint();
        }

        public BufferedImage getImage() {
            return canvasImage;
        }

        private void drawBrush(int x1, int y1, int x2, int y2) {
            g2d.setColor(currentColor);
            g2d.setStroke(new BasicStroke(strokeSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(x1, y1, x2, y2);
        }

        private void drawEraser(int x1, int y1, int x2, int y2) {
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(strokeSize * 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(x1, y1, x2, y2);
        }

        private void drawShape(Graphics2D g, String tool, int x1, int y1, int x2, int y2, Color color, boolean fill) {
            g.setColor(color);
            g.setStroke(new BasicStroke(strokeSize));

            int w = Math.abs(x2 - x1);
            int h = Math.abs(y2 - y1);
            int x = Math.min(x1, x2);
            int y = Math.min(y1, y2);

            if (tool.equals("Line")) {
                g.drawLine(x1, y1, x2, y2);
            } else if (tool.equals("Rect")) {
                if (fill) g.fillRect(x, y, w, h);
                else g.drawRect(x, y, w, h);
            } else if (tool.equals("Oval")) {
                if (fill) g.fillOval(x, y, w, h);
                else g.drawOval(x, y, w, h);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimplePaint());
    }
}