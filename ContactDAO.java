import java.io.*;

public class ContactDAO {
    private String filename;

    public ContactDAO(String filename) {
        this.filename = filename;
    }

    public void createContact(Contact contact) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(contact.getName() + "," + contact.getPhoneNumber());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Contact readContact(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(name)) {
                    return new Contact(data[0], data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateContact(String name, String newPhoneNumber) {
        File inputFile = new File(filename);
        File tempFile = new File("temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(name)) {
                    writer.write(name + "," + newPhoneNumber);
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    public void deleteContact(String name) {
        File inputFile = new File(filename);
        File tempFile = new File("temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[0].equals(name)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }
}
