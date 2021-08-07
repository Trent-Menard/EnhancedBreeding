package github.trentmenard.com.enhancedbreeding;

import org.bukkit.ChatColor;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class BreedingListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
        Animals target;
        Player player = event.getPlayer();

        if (event.getRightClicked() instanceof Animals) {
            target = (Animals) event.getRightClicked();

            if (target.isBreedItem(event.getPlayer().getInventory().getItemInMainHand()) ||
                    (target.isBreedItem(event.getPlayer().getInventory().getItemInOffHand()))) {

                if (target.getAge() > 0) { // Target CANNOT breed. Age = breeding cool-down (in ticks).
                    player.sendMessage(ChatColor.GOLD + "This " + ChatColor.YELLOW + target.getName() + ChatColor.RED + " is unable to breed " + ChatColor.GOLD + "at the moment.");

                    StringBuilder cooldownMessage = new StringBuilder("&6It will be able to breed in: ");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', buildMessage(cooldownMessage, target.getAge())));

                }
                else if (target.getAge() < 0) { // Target CANNOT breed & is a baby. Age = time to adulthood (in ticks).
                    StringBuilder cooldownMessage = new StringBuilder("&6This &e").append(target.getName()).append(" &6will become an &eadult &6in: ");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', buildMessage(cooldownMessage, Math.abs(target.getAge()))));
                }

                else if (target.getLoveModeTicks() != 0){
                    StringBuilder cooldownMessage = new StringBuilder("&6This &e").append(target.getName()).append(" &6will &cexit &6love mode in: ");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', buildMessage(cooldownMessage, target.getLoveModeTicks())));
                }
            }
        }
    }

    private String buildMessage(StringBuilder baseMessage, int targetAgeInTicks){
        int ageInTicks, ageToSeconds, ageToMinutes, minutesToSecondsRemainder;

        ageInTicks = targetAgeInTicks;
        ageInTicks += 20; // Compensate for Integer Division / Rounding Errors.
        ageToSeconds = ageInTicks / 20;
        ageToMinutes = ageInTicks / 1200;
        minutesToSecondsRemainder = ageToSeconds % 60;

        if (ageToMinutes > 1)
        {
            baseMessage.append("&e").append(ageToMinutes).append("&6");
            baseMessage.append(" minutes");
            if (minutesToSecondsRemainder > 1){
                baseMessage.append(" and ");
                baseMessage.append("&e").append(minutesToSecondsRemainder).append("&6");
                baseMessage.append(" seconds.");
            }
            else if (minutesToSecondsRemainder == 1){
                baseMessage.append(" and ");
                baseMessage.append("&e").append(minutesToSecondsRemainder).append("&6");
                baseMessage.append(" second.");
            }
            else if (minutesToSecondsRemainder == 0)
                baseMessage.append(".");
        }
        else if (ageToMinutes == 1){
            baseMessage.append("&e").append(ageToMinutes).append("&6");
            baseMessage.append(" minute");
            if (minutesToSecondsRemainder > 1){
                baseMessage.append(" and ");
                baseMessage.append("&e").append(minutesToSecondsRemainder).append("&6");
                baseMessage.append(" seconds.");
            }
            else if (minutesToSecondsRemainder == 1){
                baseMessage.append(" &e").append(ageToMinutes).append("&6");
                baseMessage.append(" second.");
            }
            else if (minutesToSecondsRemainder == 0)
            {
                baseMessage.append(".");
            }
        }
        else if (ageToMinutes == 0){
            baseMessage.append("&e").append(minutesToSecondsRemainder).append("&6");
            if (minutesToSecondsRemainder == 0 || minutesToSecondsRemainder > 1){
                baseMessage.append(" seconds.");
            }
            else if (minutesToSecondsRemainder == 1){
                baseMessage.append(" second.");
            }
        }
        return baseMessage.toString();
    }
}