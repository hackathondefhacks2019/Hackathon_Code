public class TransactionNode {
    private double amount;
    private int date;

        public TransactionNode(double amount, int date) {
            this.amount = amount;
            this.date = date;
        }

        public double getAmount() { return this.amount; }

        public int getDate() { return this.date; }
    }
}
