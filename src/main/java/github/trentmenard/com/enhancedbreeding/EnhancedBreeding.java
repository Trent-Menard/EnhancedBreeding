package github.trentmenard.com.enhancedbreeding;

import org.bukkit.plugin.java.JavaPlugin;

public final class EnhancedBreeding extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new BreedingListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
