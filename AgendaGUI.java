import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgendaGUI extends JFrame {
    private ContactFile contactFile;
    private JTextField nameField;
    private JTextField phoneNumberField;

    public AgendaGUI() {
        contactFile = new ContactFile("contacts.txt");

        // Configurar la interfaz gráfica de usuario
        setTitle("Agenda de Contactos");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());

        JLabel nameLabel = new JLabel("Nombre:");
        JTextField nameField = new JTextField(15);
        JLabel phoneNumberLabel = new JLabel("Teléfono:");
        JTextField phoneNumberField = new JTextField(15);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(nameLabel, gbc);

        gbc.gridy++;
        inputPanel.add(phoneNumberLabel, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(nameField, gbc);

        gbc.gridy++;
        inputPanel.add(phoneNumberField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton createButton = new JButton("Crear");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneNumberField.getText();
                Contacto contact = new Contacto(name, phoneNumber);
                contactFile.createContact(contact);
                clearFields();
                JOptionPane.showMessageDialog(null, "Contacto creado con éxito");
            }
        });

        JButton readButton = new JButton("Leer");
        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                Contacto contact = contactFile.readContact(name);
                if (contact != null) {
                    phoneNumberField.setText(contact.getPhoneNumber());
                } else {
                    phoneNumberField.setText("");
                    JOptionPane.showMessageDialog(null, "Contacto no encontrado");
                }
            }
        });

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneNumberField.getText();
                contactFile.updateContact(name, phoneNumber);
                clearFields();
                JOptionPane.showMessageDialog(null, "Contacto actualizado con éxito");
            }
        });

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                contactFile.deleteContact(name);
                clearFields();
                JOptionPane.showMessageDialog(null, "Contacto eliminado con éxito");
            }
        });

        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void clearFields() {
        nameField.setText("");
        phoneNumberField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AgendaGUI gui = new AgendaGUI();
                gui.setVisible(true);
            }
        });
    }
}

