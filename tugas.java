import java.util.Scanner;
import java.util.ArrayList;

class Main {
    private Book[] bookList;
    private ArrayList<User> userStudent; // Menggunakan ArrayList untuk menyimpan data mahasiswa

    public static void main(String[] args) {
        Main main = new Main();
        main.initialize();
        main.menu();
    }
    public void initialize() {
        // Inisialisasi data buku
        bookList = new Book[5];
        bookList[0] = new Book("001", "One Piece", "Eiichiro Oda", 10);
        bookList[1] = new Book("002", "Jojo Bizzare Adventure", "Hirohiko Araki", 8);
        bookList[2] = new Book("003", "Hunter X Hunter", "Yoshihiro Togashi", 12);
        bookList[3] = new Book("004", "Percy Jackson", "Rick Riordan", 6);
        bookList[4] = new Book("005", "Gosho Aoyama", "Detective Conan", 5);

        // Inisialisasi data mahasiswa dengan ArrayList
        userStudent = new ArrayList<>();
        userStudent.add(new User("Luffy", "202300011122233", "Ilmu Makanan", "Makan"));
        userStudent.add(new User("Conan", "202300011122234", "Psikologi", "Kriminologi"));
        userStudent.add(new User("Jotaro", "202300011122235", "Pendidikan Olahraga", "Tinju"));
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Library System");
        System.out.println("1. Login as Admin");
        System.out.println("2. Login as Student");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                Admin admin = new Admin(bookList, userStudent);
                admin.menuAdmin();
                break;
            case 2:
                Student student = new Student(bookList, userStudent);
                student.menuStudent();
                break;
            case 3:
                System.out.println("Exiting...");
                System.exit(0);
            default:
                System.out.println("Invalid choice");
        }
    }
}

class Book {
    private String title;
    private String author;
    private String id;
    private int stock;

    public Book(String id, String title, String author, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int i) {
    }
}

class User {
    private String name;
    private String nim;
    private String faculty;
    private String program;

    public User(String name, String nim, String faculty, String program) {
        this.name = name;
        this.nim = nim;
        this.faculty = faculty;
        this.program = program;
    }

    public String getName() {
        return name;
    }

    public String getNim() {
        return nim;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getProgram() {
        return program;
    }
}

class Student {
    private Book[] bookList;
    private ArrayList<User> userStudent;
    private ArrayList<Book> borrowedBooks;

    public Student(Book[] bookList, ArrayList<User> userStudent) {
        this.bookList = bookList;
        this.userStudent = userStudent;
        this.borrowedBooks = new ArrayList<>();
    }

    public void menuStudent() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nStudent Dashboard");
            System.out.println("1. Display Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Display Borrowed Books");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayBooks();
                    break;
                case 2:
                    borrowBook();
                    break;
                case 3:
                    displayBorrowedBooks();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void displayBooks() {
        System.out.println("\nAvailable Books:");
        for (Book book : bookList) {
            int borrowedQuantity = getBorrowedQuantity(book);
            int availableStock = book.getStock() - borrowedQuantity;
            System.out.println(book.getTitle() + " by " + book.getAuthor() + " (ID: " + book.getId() + ", Stock: " + availableStock + ")");
        }
    }

    private int getBorrowedQuantity(Book book) {
        int borrowedQuantity = 0;
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.getId().equals(book.getId())) {
                borrowedQuantity++;
            }
        }
        return borrowedQuantity;
    }


    public void borrowBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the book you want to borrow: ");
        String bookId = scanner.nextLine();
        Book borrowedBook = null;
        for (Book book : bookList) {
            if (book.getId().equals(bookId)) {
                borrowedBook = book;
                break;
            }
        }
        if (borrowedBook != null) {
            if (borrowedBook.getStock() > 0) {

                borrowedBook.setStock(borrowedBook.getStock() - 1);

                borrowedBooks.add(borrowedBook);
                System.out.println("Book " + borrowedBook.getTitle() + " has been borrowed successfully.");
            } else {
                System.out.println("Sorry, the book " + borrowedBook.getTitle() + " is out of stock.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("You haven't borrowed any books yet.");
        } else {
            System.out.println("\nBorrowed Books:");
            for (Book book : borrowedBooks) {
                System.out.println(book.getTitle() + " by " + book.getAuthor());
            }
        }
    }
}
class Admin {
    private Book[] bookList;
    private ArrayList<User> userStudent;
    private int studentCount;

    public Admin(Book[] bookList, ArrayList<User> userStudent) {
        this.bookList = bookList;
        this.userStudent = userStudent;
        this.studentCount = 0;
    }

    public void menuAdmin() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdmin Dashboard");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addStudent();
                    displayStudents();
                    break;
                case 2:
                    displayStudents();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void addStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adding Student");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter NIM: ");
        String nim = scanner.nextLine();
        if (nim.length() != 15) {
            System.out.println("NIM must be 15 characters long.");
            return;
        }
        System.out.print("Enter faculty: ");
        String faculty = scanner.nextLine();
        System.out.print("Enter program: ");
        String program = scanner.nextLine();

        User newUser = new User(name, nim, faculty, program);
        userStudent.add(newUser);

        System.out.println("Student added successfully.");
    }
    public void displayStudents() {
        System.out.println("\nRegistered Students:");
        for (User user : userStudent) {
            if (user != null) {
                System.out.println(user.getName() + " - " + user.getNim() + " - " + user.getFaculty() + " - " + user.getProgram());
            }
        }
    }

}
