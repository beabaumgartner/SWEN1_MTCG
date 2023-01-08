package at.fhtw.mtcgapp.model;

import java.util.List;

public class Battle {
    Integer battle_id;
    User playerA;
    User playerB;
    String battle_log;

    public Battle(Integer battle_id, User playerA, User playerB) {
        this.battle_id = battle_id;
        this.playerA = playerA;
        this.playerB = playerB;
        battle_log = "";
    }

    public String getBattle_log() { return battle_log; }

    public void setBattleLog(String battle_log) { this.battle_log += battle_log; }

    public String calculateWinner(List<Card> playerDeckCard)
    {
        Card AplayerDeckCard = playerDeckCard.get(0);
        Card BplayerDeckCard = playerDeckCard.get(1);

        this.battle_log += ("        " + this.playerA.getUsername() + ": " +
                AplayerDeckCard.getName() + " (" + AplayerDeckCard.getDamage() + " Damage) " +
                this.playerB.getUsername() + ": " +
                BplayerDeckCard.getName() + " (" + BplayerDeckCard.getDamage() + " Damage)" +
                "\n        ");

        String winner = manageSpecialities(AplayerDeckCard ,BplayerDeckCard);
        if(winner != null)
        {
            return winner;
        }

        if(AplayerDeckCard.getCardType().equals("spell") ||
                BplayerDeckCard.getCardType().equals("spell"))
        {
            return calculateSpellFight(AplayerDeckCard, BplayerDeckCard);
        }

        return calculateFight(AplayerDeckCard.getDamage(), BplayerDeckCard.getDamage());
    }

    private String calculateSpellFight(Card AplayerDeckCard, Card BplayerDeckCard)
    {
        double AplayerDamage = 0;
        double BplayerDamage = 0;

        switch (AplayerDeckCard.getElementType())
        {
            case "water":
                switch (BplayerDeckCard.getElementType())
                {
                    case "water":
                        AplayerDamage = AplayerDeckCard.getDamage();
                        BplayerDamage = BplayerDeckCard.getDamage();

                        battle_log += "=> " + AplayerDeckCard.getDamage() + " vs " + BplayerDeckCard.getDamage() + " -> " +
                                (int)AplayerDamage + " vs " + (int)BplayerDamage + " ";
                        break;
                    case "fire":
                        AplayerDamage = AplayerDeckCard.getDamage() * 2;
                        BplayerDamage = BplayerDeckCard.getDamage() * 0.5;

                        battle_log += "=> " + AplayerDeckCard.getDamage() + " vs " + BplayerDeckCard.getDamage() + " -> " +
                                (int)AplayerDamage + " vs " + (int)BplayerDamage + " ";
                        break;
                    case "normal":
                        AplayerDamage = AplayerDeckCard.getDamage() * 0.5;
                        BplayerDamage = BplayerDeckCard.getDamage() * 2;

                        battle_log += "=> " + AplayerDeckCard.getDamage() + " vs " + BplayerDeckCard.getDamage() + " -> " +
                                (int)AplayerDamage + " vs " + (int)BplayerDamage + " ";
                        break;
                    default:
                        break;
                }
                break;
            case "fire":
                switch (BplayerDeckCard.getElementType())
                {
                    case "water":
                        AplayerDamage = AplayerDeckCard.getDamage() * 0.5;
                        BplayerDamage = BplayerDeckCard.getDamage() * 2;

                        battle_log += "=> " + AplayerDeckCard.getDamage() + " vs " + BplayerDeckCard.getDamage() + " -> " +
                                (int)AplayerDamage + " vs " + (int)BplayerDamage + " ";
                        break;
                    case "fire":
                        AplayerDamage = AplayerDeckCard.getDamage();
                        BplayerDamage = BplayerDeckCard.getDamage();

                        battle_log += "=> " + AplayerDeckCard.getDamage() + " vs " + BplayerDeckCard.getDamage() + " -> " +
                                (int)AplayerDamage + " vs " + (int)BplayerDamage + " ";
                        break;
                    case "normal":
                        AplayerDamage = AplayerDeckCard.getDamage() * 2;
                        BplayerDamage = BplayerDeckCard.getDamage() * 0.5;

                        battle_log += "=> " + AplayerDeckCard.getDamage() + " vs " + BplayerDeckCard.getDamage() + " -> " +
                                (int)AplayerDamage + " vs " + (int)BplayerDamage + " ";
                        break;
                    default:
                        break;
                }
                break;
            case "normal":
                switch (BplayerDeckCard.getElementType())
                {
                    case "water":
                        AplayerDamage = AplayerDeckCard.getDamage() * 2;
                        BplayerDamage = BplayerDeckCard.getDamage() * 0.5;

                        battle_log += "=> " + AplayerDeckCard.getDamage() + " vs " + BplayerDeckCard.getDamage() + " -> " +
                                (int)AplayerDamage + " vs " + (int)BplayerDamage + " ";
                        break;
                    case "fire":
                        AplayerDamage = AplayerDeckCard.getDamage() * 0.5;
                        BplayerDamage = BplayerDeckCard.getDamage() * 2;

                        battle_log += "=> " + AplayerDeckCard.getDamage() + " vs " + BplayerDeckCard.getDamage() + " -> " +
                                (int)AplayerDamage + " vs " + (int)BplayerDamage + " ";
                        break;
                    case "normal":
                        AplayerDamage = AplayerDeckCard.getDamage();
                        BplayerDamage = BplayerDeckCard.getDamage();

                        battle_log += "=> " + AplayerDeckCard.getDamage() + " vs " + BplayerDeckCard.getDamage() + " -> " +
                                (int)AplayerDamage + " vs " + (int)BplayerDamage + " ";
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }

        return calculateFight(AplayerDamage, BplayerDamage);
    }

    private String calculateFight(double AplayerDamage, double BplayerDamage)
    {
        if((int)AplayerDamage > (int)BplayerDamage)
        {
            return "playerA";
        }
        else if ((int)BplayerDamage > (int)AplayerDamage)
        {
            return "playerB";
        }
        else if ((int)AplayerDamage == (int)BplayerDamage)
        {
            return "draw";
        }

        return null;
    }

    private String manageSpecialities(Card AplayerDeckCard, Card BplayerBDeckCard)
    {
        if(AplayerDeckCard.getName().toLowerCase().contains("goblin") &&
                BplayerBDeckCard.getName().toLowerCase().contains("dragon"))
        {
            return "playerB";
        }
        else if(BplayerBDeckCard.getName().toLowerCase().contains("goblin") &&
                AplayerDeckCard.getName().toLowerCase().contains("dragon"))
        {
            return "playerA";
        }
        else if(AplayerDeckCard.getName().toLowerCase().contains("wizzard") &&
                BplayerBDeckCard.getName().toLowerCase().contains("ork"))
        {
            return "playerA";
        }
        else if(BplayerBDeckCard.getName().toLowerCase().contains("wizzard") &&
                AplayerDeckCard.getName().toLowerCase().contains("ork"))
        {
            return "playerB";
        }
        else if(AplayerDeckCard.getName().toLowerCase().contains("knight") &&
               (BplayerBDeckCard.getName().toLowerCase().contains("water") &&
                BplayerBDeckCard.getName().toLowerCase().contains("spell")))
        {
            return "playerB";
        }
        else if(BplayerBDeckCard.getName().toLowerCase().contains("knight") &&
                (AplayerDeckCard.getName().toLowerCase().contains("water") &&
                 AplayerDeckCard.getName().toLowerCase().contains("spell")))
        {
            return "playerA";
        }
        else if(AplayerDeckCard.getName().toLowerCase().contains("kraken") &&
                BplayerBDeckCard.getName().toLowerCase().contains("spell"))
        {
            return "playerA";
        }
        else if(BplayerBDeckCard.getName().toLowerCase().contains("kraken") &&
                AplayerDeckCard.getName().toLowerCase().contains("spell"))
        {
            return "playerA";
        }
        else if(AplayerDeckCard.getName().toLowerCase().contains("FireElf") &&
                BplayerBDeckCard.getName().toLowerCase().contains("dragon"))
        {
            return "playerA";
        }
        else if(BplayerBDeckCard.getName().toLowerCase().contains("FireElf") &&
                AplayerDeckCard.getName().toLowerCase().contains("dragon"))
        {
            return "playerB";
        }

        return null;
    }
}
