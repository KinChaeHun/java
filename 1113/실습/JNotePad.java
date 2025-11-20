import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JNotePad extends JFrame {
    private JTextPane _textPane;
    private ActionMap _actionMap;
    private boolean _isSaved = true;
    private JFileChooser _fc = new JFileChooser(".");
    private File _file = null;

    public JNotePad() {
        super("JNotePad");
        _textPane = new JTextPane();
        _textPane.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { _isSaved = false; }
            public void removeUpdate(DocumentEvent e) { _isSaved = false; }
            public void changedUpdate(DocumentEvent e) { _isSaved = false; }
        });
        JScrollPane p = new JScrollPane(_textPane);
        add(p);
        _actionMap = createActionMap();
        setJMenuBar(createMenuBar());
        add(createToolBar(), BorderLayout.NORTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();

        // File 메뉴
        JMenu m = new JMenu("파일");
        m.add(new JMenuItem(_actionMap.get("새파일")));
        m.add(new JMenuItem(_actionMap.get("열기")));
        m.add(new JMenuItem(_actionMap.get("저장")));
        m.add(new JMenuItem(_actionMap.get("다른이름으로 저장")));
        m.addSeparator();
        m.add(new JMenuItem(_actionMap.get("종료")));
        menubar.add(m);

        // Edit 메뉴
        m = new JMenu("수정");
        m.add(new JMenuItem(_actionMap.get("잘라내기")));
        m.add(new JMenuItem(_actionMap.get("복사")));
        m.add(new JMenuItem(_actionMap.get("붙여넣기")));
        m.add(new JMenuItem(_actionMap.get("찾기")));
        m.add(new JMenuItem(_actionMap.get("바꾸기")));
        menubar.add(m);

        // Help 메뉴
        m = new JMenu("도움말");
        m.add(new JMenuItem(_actionMap.get("정보")));
        m.add(new JMenuItem(_actionMap.get("도움말")));
        menubar.add(m);

        return menubar;
    }

    private JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.add(new JButton(_actionMap.get("새파일")));
        toolbar.add(new JButton(_actionMap.get("열기")));
        toolbar.add(new JButton(_actionMap.get("저장")));
        toolbar.add(new JButton(_actionMap.get("다른이름으로 저장")));
        toolbar.addSeparator();

        toolbar.add(new JButton(_actionMap.get("복사")));
        toolbar.add(new JButton(_actionMap.get("잘라내기")));
        toolbar.add(new JButton(_actionMap.get("붙여넣기")));
        toolbar.addSeparator();

        toolbar.add(new JButton(_actionMap.get("정보")));
        toolbar.add(new JButton(_actionMap.get("도움말")));

        Component[] comps = toolbar.getComponents();
        for (Component c : comps) {
            if (c instanceof JButton)
                c.setFocusable(false);
        }
        return toolbar;
    }

    // ---------------- Actions ----------------
    private class NewAction extends AbstractAction {
        public NewAction() { super("새파일"); }
        public void actionPerformed(ActionEvent e) {
            if (!confirmSave()) return;
            _textPane.setText("");
            _isSaved = true;
            _file = null;
            setTitle("JNotePad");
        }
    }

    private class OpenAction extends AbstractAction {
        public OpenAction() { super("열기"); }
        public void actionPerformed(ActionEvent e) {
            if (!confirmSave()) return;
            open();
        }
    }

    private class SaveAction extends AbstractAction {
        public SaveAction() { super("저장"); }
        public void actionPerformed(ActionEvent e) { save(); }
    }

    private class SaveAsAction extends AbstractAction {
        public SaveAsAction() { super("다른이름으로 저장"); }
        public void actionPerformed(ActionEvent e) { saveAs(); }
    }

    private class ExitAction extends AbstractAction {
        public ExitAction() { super("종료"); }
        public void actionPerformed(ActionEvent e) {
            if (!confirmSave()) return;
            System.exit(0);
        }
    }

    private class CutAction extends AbstractAction {
        public CutAction() { super("잘라내기"); }
        public void actionPerformed(ActionEvent e) { _textPane.cut(); }
    }

    private class CopyAction extends AbstractAction {
        public CopyAction() { super("복사"); }
        public void actionPerformed(ActionEvent e) { _textPane.copy(); }
    }

    private class PasteAction extends AbstractAction {
        public PasteAction() { super("붙여넣기"); }
        public void actionPerformed(ActionEvent e) { _textPane.paste(); }
    }

    // ---------------- Find & Replace ----------------
    private class FindAction extends AbstractAction {
        public FindAction() { super("찾기"); }
        public void actionPerformed(ActionEvent e) {
            String search = JOptionPane.showInputDialog(JNotePad.this, "찾을 문자열 입력:");
            if (search == null || search.isEmpty()) return;
            String text = _textPane.getText();
            int index = text.indexOf(search, _textPane.getCaretPosition());
            if (index >= 0) {
                _textPane.select(index, index + search.length());
            } else {
                JOptionPane.showMessageDialog(JNotePad.this, "찾을 수 없습니다.");
            }
        }
    }

    private class ReplaceAction extends AbstractAction {
        public ReplaceAction() { super("바꾸기"); }
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JTextField findField = new JTextField(10);
            JTextField replaceField = new JTextField(10);
            panel.add(new JLabel("찾기:"), BorderLayout.WEST);
            panel.add(findField, BorderLayout.CENTER);
            panel.add(new JLabel("바꾸기:"), BorderLayout.SOUTH);
            panel.add(replaceField, BorderLayout.SOUTH);

            int result = JOptionPane.showConfirmDialog(JNotePad.this, panel, "바꾸기", JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION) return;

            String findText = findField.getText();
            String replaceText = replaceField.getText();
            if (findText.isEmpty()) return;

            String content = _textPane.getText();
            _textPane.setText(content.replace(findText, replaceText));
        }
    }

    // ---------------- Help ----------------
    private class AboutAction extends AbstractAction {
        public AboutAction() { super("정보"); }
        public void actionPerformed(ActionEvent e) {
            String[] mesg = {"텍스트 에디터 v1.1", "제작자: 김채훈"};
            JOptionPane.showMessageDialog(JNotePad.this, mesg, "정보", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class HelpAction extends AbstractAction {
        public HelpAction() { super("도움말"); }
        public void actionPerformed(ActionEvent e) {
            String mesg = "기능 설명:\n"
                    + "새파일: 새로운 문서를 생성합니다.\n"
                    + "열기: 기존 파일을 엽니다.\n"
                    + "저장: 현재 내용을 저장합니다.\n"
                    + "다른이름으로 저장: 다른 이름으로 저장합니다.\n"
                    + "잘라내기: 선택한 내용을 잘라냅니다.\n"
                    + "복사: 선택한 내용을 복사합니다.\n"
                    + "붙여넣기: 복사한 내용을 붙여넣습니다.\n"
                    + "찾기: 입력한 문자열을 문서에서 찾습니다.\n"
                    + "바꾸기: 입력한 문자열을 다른 문자열로 바꿉니다.\n"
                    + "종료: 프로그램을 종료합니다.";
            JOptionPane.showMessageDialog(JNotePad.this, mesg, "도움말", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ---------------- File Handling ----------------
    private boolean confirmSave() {
        if (_isSaved) return true;
        int ret = JOptionPane.showConfirmDialog(this, "내용이 수정되었습니다. 저장하시겠습니까?", "저장 확인", JOptionPane.YES_NO_CANCEL_OPTION);
        if (ret == JOptionPane.YES_OPTION) { save(); return true; }
        if (ret == JOptionPane.NO_OPTION) return true;
        return false;
    }

    private void open() {
        if (_fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = _fc.getSelectedFile();
            try {
                BufferedReader r = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                char[] buf = new char[1024];
                int n;
                while ((n = r.read(buf)) != -1) sb.append(buf, 0, n);
                r.close();
                _textPane.setText(sb.toString());
                _file = file;
                setTitle(file.getName() + " - JNotePad");
                _isSaved = true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "파일을 열 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void save() {
        if (_file == null) saveAs();
        else {
            try {
                BufferedWriter w = new BufferedWriter(new FileWriter(_file));
                w.write(_textPane.getText());
                w.close();
                _isSaved = true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "저장할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveAs() {
        if (_fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            _file = _fc.getSelectedFile();
            save();
            setTitle(_file.getName() + " - JNotePad");
        }
    }

    private ActionMap createActionMap() {
        ActionMap am = new ActionMap();
        am.put("정보", new AboutAction());
        am.put("도움말", new HelpAction());
        am.put("잘라내기", new CutAction());
        am.put("복사", new CopyAction());
        am.put("붙여넣기", new PasteAction());
        am.put("종료", new ExitAction());
        am.put("새파일", new NewAction());
        am.put("열기", new OpenAction());
        am.put("저장", new SaveAction());
        am.put("다른이름으로 저장", new SaveAsAction());
        am.put("찾기", new FindAction());
        am.put("바꾸기", new ReplaceAction());
        return am;
    }

    private void start() {
        setSize(700, 480);
        setLocation(100, 100);
        setVisible(true);
    }

    public static void main(String[] args) {
        new JNotePad().start();
    }
}
