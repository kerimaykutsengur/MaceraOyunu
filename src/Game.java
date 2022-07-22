import java.util.Scanner;

public class Game {
    private Scanner input = new Scanner(System.in);

    public void start() {
        System.out.println("Macera Oyununa Hoşgeldiniz !");
        System.out.print("Lütfen bir isim giriniz :");
        String playerName = input.nextLine();
        Player player = new Player(playerName);
        System.out.println("Maceracı " + player.getName() + " Hoşgeldin !");
        System.out.println("Lütfen bir karakter seçiniz !");
        System.out.println("--------------------------------------------------------------------");
        player.selectChar();

        Location location = null;

        while (true) {
            player.printInfo();
            System.out.println();
            System.out.println("-------------------------- Bölgeler ----------------------------");
            System.out.println();
            System.out.println("1 - Güvenli Ev --> Burada canınız yenilenecektir !");
            System.out.println("2 - Mağaza --> Burada silah veya zırh satın alabilirsiniz !");
            System.out.println("3 - Zombi dolu mağara ! Ödül : Food ");
            System.out.println("4 - Orman Vampirlerle dolu ! Ödül : Firewood ");
            System.out.println("5 - Ayılar nehir kenarını sever ! Ödül : Water");
            System.out.println("6 - Yılanlı maden ! Ödül : Item düşürme şansı");
            System.out.println("0 - Çıkış Yap --> Oyunu sonlandırabilirsiniz !");
            System.out.println();
            System.out.print("Lütfen gitmek istediğiniz bölgeyi seçiniz : ");
            int selectLoc = input.nextInt();
            switch (selectLoc) {
                case 0:
                    location = null;
                    break;
                case 1:
                    location = new SafeHouse(player);
                    break;
                case 2:
                    location = new ToolsStore(player);
                    break;
                case 3:
                    location = new Cave(player);
                    break;
                case 4:
                    location = new Forest(player);
                    break;
                case 5:
                    location = new River(player);
                    break;
                case 6:
                    location = new Quarry(player);
                    default:
                    System.out.println("Lütfen geçerli bir bölge seçiniz !");
            }
            if (location == null){
                System.out.println("Oyun sonlandırıldı! Yine bekleriz :)");
                break;
            }
            if(location.getClass().getName().equals("SafeHouse")){
                if (player.getInventory().isFood() && player.getInventory().isFirewood() && player.getInventory().isWater()){
                    System.out.println("Tebrikler Maceracı " + player.getName()+" !!!");
                    System.out.println("Bu zorlu macerayı tamamladın !!!");
                    break;
                }
            }
            if (location.getClass().getName().equals("Cave")){
                if(player.getInventory().isFood()){
                    System.out.println("Bu bölgedeki ödülü kazandın ! Tekrar giremezsin !");
                    continue;
                }
            }
            if (location.getClass().getName().equals("Forest")){
                if(player.getInventory().isFirewood()){
                    System.out.println("Bu bölgedeki ödülü kazandın ! Tekrar giremezsin !");
                    continue;
                }
            }
            if (location.getClass().getName().equals("River")) {
                if (player.getInventory().isWater()) {
                    System.out.println("Bu bölgedeki ödülü kazandın ! Tekrar giremezsin !");
                    continue;
                }
            }

            if (!location.onLocation()) {
                System.out.println("GAME OVER!!!");
                break;
            }
        }




    }
}
