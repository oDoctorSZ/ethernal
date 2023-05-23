package ian.tomaz.ethernal.utils;

import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.models.RegionModel;
import ian.tomaz.ethernal.models.TransactionsModel;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EthernalMaps {

    public static List<Player> typingList = new ArrayList<>();
    public static List<Player> currentInTransfer = new ArrayList<>();
    public static List<String> currentInDeposit = new ArrayList<>();
    public static List<String> currentInCashout= new ArrayList<>();
    public static List<RegionModel> regions  = new ArrayList<>();
    public static HashMap<String, PlayerModel> playerHash = new HashMap<>();
    public static List<TransactionsModel> transactionsList = new ArrayList<>();


}
