import java.util.ArrayList;
import java.util.Scanner;

class User {
    String name, address, phone;

    User(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    void display() {
        System.out.println("\n--- Customer Details ---");
        System.out.println("Customer: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phone);
    }
}

class FoodItem {
    String name;
    int price;

    FoodItem(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

class Restaurant {
    String name;
    FoodItem[] menu;

    Restaurant(String name, FoodItem[] menu) {
        this.name = name;
        this.menu = menu;
    }

    void showMenu() {
        System.out.println("\n--- " + name + " Menu ---");
        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + ". " + menu[i].name + " - ₹" + menu[i].price);
        }
    }
}

class CartItem {
    FoodItem item;
    int quantity;

    CartItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}

class Cart {
    ArrayList<CartItem> itemsList = new ArrayList<>();
    int total = 0;

    void addItem(FoodItem item, int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }

        for (CartItem ci : itemsList) {
            if (ci.item.name.equals(item.name)) {
                ci.quantity += quantity;
                total += item.price * quantity;
                System.out.println("Updated " + item.name + " quantity.");
                return;
            }
        }

        itemsList.add(new CartItem(item, quantity));
        total += item.price * quantity;
        System.out.println(item.name + " added to cart.");
    }

    void showCart() {
        if (itemsList.isEmpty()) {
            System.out.println("\nCart is empty!");
            return;
        }

        System.out.println("\n------ YOUR CART ------");
        for (CartItem ci : itemsList) {
            int cost = ci.item.price * ci.quantity;
            System.out.println(ci.item.name + " x " + ci.quantity + " = ₹" + cost);
        }
        System.out.println("Current Total: ₹" + total);
    }

    boolean generateBill(User user, Scanner sc) {
        if (itemsList.isEmpty()) {
            System.out.println("\nYour cart is empty!");
            return false;
        }

        user.display();

        System.out.println("\n------- BILL -------");
        for (CartItem ci : itemsList) {
            int cost = ci.item.price * ci.quantity;
            System.out.println(ci.item.name + " x " + ci.quantity + " = ₹" + cost);
        }

        double gst = total * 0.05;
        double discount = (total > 300) ? total * 0.10 : 0;

        System.out.print("\nEnter distance (km) for delivery: ");
        int distance = sc.nextInt();
        int delivery = (distance <= 5) ? 30 : 60;

        double finalBill = total + gst + delivery - discount;

        System.out.println("----------------------");
        System.out.println("Subtotal: ₹" + total);
        System.out.printf("GST (5%%): ₹%.2f\n", gst);
        System.out.printf("Discount: -₹%.2f\n", discount);
        System.out.println("Delivery Charge: ₹" + delivery);
        System.out.printf("Final Amount: ₹%.2f\n", finalBill);

        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Cash on Delivery");
        System.out.println("2. UPI");
        System.out.print("Enter choice: ");
        int pay = sc.nextInt();

        if (pay == 1) {
            System.out.println("\nPayment: Cash on Delivery");
        } else if (pay == 2) {
            System.out.println("\nPayment: UPI");
        } else {
            System.out.println("\nInvalid payment option! Defaulting to Cash.");
        }

        try {
            System.out.println("Order Placed...");
            Thread.sleep(1000);
            System.out.println("Preparing Food...");
            Thread.sleep(1000);
            System.out.println("Out for Delivery...");
            Thread.sleep(1000);
            System.out.println("Delivered!");
        } catch (Exception e) {
            System.out.println("Tracking error!");
        }

        System.out.println("\nThank You for Ordering!");
        return true;
    }
}

public class FoodOrderingSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--Welcome to CraveCart--");
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        User user = new User(name, address, phone);

        Restaurant r1 = new Restaurant("Meghana foods", new FoodItem[]{
                new FoodItem("Meghana special Boneless chicken biryani", 370),
                new FoodItem("Mutton keema biryani", 470)
        });

        Restaurant r2 = new Restaurant("The Bistro", new FoodItem[]{
                new FoodItem("Prawns sukka", 575),
                new FoodItem("Pasta", 360)
        });

        Restaurant r3 = new Restaurant("The Bier Library", new FoodItem[]{
                new FoodItem("Apple and Rocket Salad", 309),
                new FoodItem("Alfredo Pasta", 359)
        });

        Restaurant[] restaurants = {r1, r2, r3};

        System.out.println("\nSelect Restaurant:");
        for (int i = 0; i < restaurants.length; i++) {
            System.out.println((i + 1) + ". " + restaurants[i].name);
        }
        System.out.print("Enter choice: ");
        int rChoice = sc.nextInt();

        if (rChoice < 1 || rChoice > restaurants.length) {
            System.out.println("Invalid restaurant choice! Exiting...");
            sc.close();
            return;
        }

        Restaurant selected = restaurants[rChoice - 1];
        Cart cart = new Cart();
        int choice;

        do {
            selected.showMenu();

            System.out.println("\n1. Add Item");
            System.out.println("2. View Cart");
            System.out.println("3. Checkout");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter item number: ");
                    int item = sc.nextInt();
                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();

                    if (item >= 1 && item <= selected.menu.length) {
                        cart.addItem(selected.menu[item - 1], qty);
                    } else {
                        System.out.println("Invalid item!");
                    }
                    break;

                case 2:
                    cart.showCart();
                    break;

                case 3:
                    if (cart.generateBill(user, sc)) {
                        choice = 4;
                    }
                    break;

                case 4:
                    System.out.println("Exiting... Have a great day!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        sc.close();
    }
}