import java.util.Random;

public abstract class BattleLoc extends Location {
    private Obstacle obstacle;
    private String award;

    private int maxObstacle;

    public BattleLoc(Player player, String name, Obstacle obstacle, String award, int maxObstacle) {
        super(player, name);
        this.obstacle = obstacle;
        this.award = award;
        this.maxObstacle = maxObstacle;
    }

    @Override
    public boolean onLocation() {
        int obsNumber = this.randomObstacleNumber();
        System.out.println("Şuan buradasın --> " + this.getName());
        System.out.println("Gözlerini dört aç!!! Çünkü burada " + obsNumber + " tane " + this.getObstacle().getName() + " yaşıyor !");
        System.out.print("<S>avaş veya <K>aç :");
        String selectCase = input.nextLine();
        selectCase = selectCase.toUpperCase();
        if (selectCase.equals("S") && combat(obsNumber)) {
            System.out.println(this.getName() + " canavarlardan temizlendi !!!");
            if (this.award.equals("Food") && getPlayer().getInventory().isFood() == false) {
                System.out.println(this.award + " Kazandınız ! ");
                getPlayer().getInventory().setFood(true);
            } else if (this.award.equals("Firewood") && getPlayer().getInventory().isFirewood() == false) {
                System.out.println(this.award + " Kazandınız ! ");
                getPlayer().getInventory().setFirewood(true);
            } else if (this.award.equals("Water") && getPlayer().getInventory().isWater() == false) {
                System.out.println(this.award + " Kazandınız ! ");
                getPlayer().getInventory().setWater(true);
            }
            return true;
        }


        if (this.getPlayer().getHealth() <= 0) {
            System.out.println("Öldün !!!");
            return false;

        }
        return true;
    }

    public boolean combat(int obsNumber) {
        for (int i = 1; i <= obsNumber; i++) {
            this.getObstacle().setHealth(this.getObstacle().getOrgHealth());
            playerStats();
            obstacleStats(i);
            double a = Math.random();
            if (a >= 0.5) {
                System.out.println("İlk vuruş senin !");
                while (this.getPlayer().getHealth() > 0 && this.getObstacle().getHealth() > 0) {
                    System.out.println("<V>ur veya <K>aç");
                    String selectCombat = input.nextLine().toUpperCase();
                    if (selectCombat.equals("V")) {
                        System.out.println("Vurdunuz !!!");
                        obstacle.setHealth(this.getObstacle().getHealth() - this.getPlayer().getTotalDamage());
                        afterHit();
                        if (this.getObstacle().getHealth() > 0) {
                            System.out.println();
                            System.out.println("Canavar size vurdu !!!");
                            int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
                            if (obstacleDamage < 0) {
                                obstacleDamage = 0;
                            }
                            this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
                            afterHit();
                        }
                    } else {
                        return false;
                    }
                }
                if (this.getObstacle().getHealth() < this.getPlayer().getHealth()) {
                    System.out.println("Düşmanı yok ettiniz !!!");
                    System.out.println(this.getObstacle().getAward() + " para kazandınız. ");
                    this.getPlayer().setMoney(this.getPlayer().getMoney() + this.getObstacle().getAward());
                    System.out.println("Güncel paranız : " + this.getPlayer().getMoney());
                } else {
                    return false;
                }
                System.out.println("-------------------------------------------------------------------------------------");
            } else {
                System.out.println("İlk vuruş canavarın !");
                while (this.getPlayer().getHealth() > 0 && this.getObstacle().getHealth() > 0) {
                    System.out.println("<V>ur veya <K>aç");
                    String selectCombat = input.nextLine().toUpperCase();
                    if (selectCombat.equals("V")) {
                        System.out.println("Canavar vurdu !!!");
                        this.getPlayer().setHealth(this.getPlayer().getHealth() - this.getObstacle().getDamage());
                        afterHit();
                        if (this.getPlayer().getHealth() > 0) {
                            System.out.println();
                            System.out.println("Vurdunuz !!!");
                            obstacle.setHealth(this.getObstacle().getHealth() - this.getPlayer().getTotalDamage());
                            afterHit();
                        }
                    } else {
                        return false;
                    }
                }
                if (this.getObstacle().getHealth() < this.getPlayer().getHealth()) {
                    System.out.println("Düşmanı yok ettiniz !!!");
                    System.out.println(this.getObstacle().getAward() + " para kazandınız. ");
                    this.getPlayer().setMoney(this.getPlayer().getMoney() + this.getObstacle().getAward());
                    System.out.println("Güncel paranız : " + this.getPlayer().getMoney());
                } else {
                    return false;
                }
                System.out.println("-------------------------------------------------------------------------------------");
            }


        }
        return true;
    }

    public void afterHit() {
        System.out.println("Canınız : " + this.getPlayer().getHealth());
        System.out.println(this.getObstacle().getName() + " Canı : " + this.getObstacle().getHealth());
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    public void playerStats() {
        System.out.println("Oyuncu Değerleri");
        System.out.println("-----------------------------------");
        System.out.println("Sağlık : " + this.getPlayer().getHealth());
        System.out.println("Silah :" + this.getPlayer().getInventory().getWeapon().getName());
        System.out.println("Hasar : " + this.getPlayer().getTotalDamage());
        System.out.println("Zırh : " + this.getPlayer().getInventory().getArmor().getName());
        System.out.println("Block : " + this.getPlayer().getInventory().getArmor().getBlock());
        System.out.println("Para : " + this.getPlayer().getMoney());
        System.out.println();
    }

    public void obstacleStats(int i) {
        System.out.println(i + ". " + this.getObstacle().getName() + " Değerleri");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Sağlık : " + this.getObstacle().getHealth());
        System.out.println("Hasar : " + this.getObstacle().getDamage());
        System.out.println("Ödül : " + this.getObstacle().getAward());
        System.out.println();
    }

    public int randomObstacleNumber() {
        Random r = new Random();
        return r.nextInt(this.getMaxObstacle()) + 1;
    }


    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public int getMaxObstacle() {
        return maxObstacle;
    }

    public void setMaxObstacle(int maxObstacle) {
        this.maxObstacle = maxObstacle;
    }


}


