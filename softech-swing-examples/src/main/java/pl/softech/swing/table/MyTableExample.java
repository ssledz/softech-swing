package pl.softech.swing.table;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pl.softech.swing.table.TableColumn.Policy;
import pl.softech.swing.window.Utils;

/**
 *
 * @author Sławomir Śledż
 * @since 1.0
 */
public class MyTableExample {

    public static class Friend {

        @TableColumn(name = "Imie", editable = true)
        public String firstname;
        @TableColumn(name = "Nazwisko")
        public String lastName;
        @TableColumn(name = "Wiek", editable = true)
        public Integer age;
        @TableColumn(name = "Wzrost", editable = true)
        public Float height;

        public Friend(String firstname, String lastName, int age, float height) {
            this.firstname = firstname;
            this.lastName = lastName;
            this.age = age;
            this.height = height;
        }
    }

    public static class Person {

        @TableColumn(name = "Imie", editable = true)
        public String firstname;
        @TableColumn(name = "Nazwisko")
        public String lastName;
        @TableColumn(name = "Waga")
        public Float weight;
        @TableColumn(name = "Przyjaciel", policy = Policy.CHILDREN_FOLLOW)
        public Friend friend;

        public Person(String firstname, String lastName, float weight) {
            this.firstname = firstname;
            this.lastName = lastName;
            this.weight = weight;
        }

        public Person(String firstname, String lastName, float weight, Friend friend) {
            this(firstname, lastName, weight);
            this.friend = friend;
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        ArrayList<Person> data = new ArrayList<Person>();
        for (int i = 0; i < 10; i++) {
            data.add(new Person("Slawek", "Sledz", rand.nextInt(100), new Friend("Kornel",
                    "Milczek", 10 + rand.nextInt(40), rand.nextFloat() * 10 + 1f)));
            data.add(new Person("Tomek", "Sledz", rand.nextInt(100), new Friend("Adam",
                    "Malek", 10 + rand.nextInt(40), rand.nextFloat() * 10 + 1f)));
        }
        MyTable<Person> table = new MyTable<Person>(Person.class);
        table.setData(data);
        JPanel content = new JPanel(new BorderLayout());
        content.add(table);
        JFrame f = Utils.getJFrame(content, "Simple Table", 1024, 768);
        f.setVisible(true);

    }
}
