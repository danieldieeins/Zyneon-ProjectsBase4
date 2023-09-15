package live.nerotv.projectsbase.modules.economy.utils;

import java.util.List;
import java.util.UUID;
public interface Economy {

    boolean createAccount(UUID paramUUID);
    boolean hasAccount(UUID paramUUID);
    boolean delete(UUID paramUUID);
    boolean withdraw(UUID paramUUID, double paramDouble);
    boolean deposit(UUID paramUUID, double paramDouble);
    boolean set(UUID paramUUID, double paramDouble);
    boolean has(UUID paramUUID, double paramDouble);
    PlayerBalance getBalance(UUID paramUUID);
    List<PlayerBalance> getPlayers();
}