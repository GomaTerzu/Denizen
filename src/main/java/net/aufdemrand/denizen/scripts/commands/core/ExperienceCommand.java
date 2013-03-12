package net.aufdemrand.denizen.scripts.commands.core;

import net.aufdemrand.denizen.exceptions.CommandExecutionException;
import net.aufdemrand.denizen.exceptions.InvalidArgumentsException;
import net.aufdemrand.denizen.npc.traits.HungerTrait;
import net.aufdemrand.denizen.scripts.ScriptEntry;
import net.aufdemrand.denizen.scripts.commands.AbstractCommand;
import net.aufdemrand.denizen.utilities.arguments.aH;
import net.aufdemrand.denizen.utilities.arguments.aH.ArgumentType;
import net.aufdemrand.denizen.utilities.debugging.dB;
import net.aufdemrand.denizen.utilities.debugging.dB.Messages;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Feeds a (Player) entity.
 * 
 * @author Jeremy Schroeder, Mason Adkins
 */

public class ExperienceCommand extends AbstractCommand {

    @Override
    public void onEnable() {
        // nothing to do here
    }

    /* FEED (AMT:#) (TARGET:NPC|PLAYER) */

    /* 
     * Arguments: [] - Required, () - Optional 
     * (AMT:#) 1-20, usually.
     * (TARGET:NPC|PLAYER) Specifies which object is the target of the feeding effects. 
     *          Default: Player, unless not available
     *   
     * Example Usage:
     * FEED AMT:20 TARGET:NPC
     * FEED AMT:5
     * FEED
     *
     */

    private enum Type { SET, GIVE, TAKE }

    @Override
    public void parseArgs(ScriptEntry scriptEntry) throws InvalidArgumentsException {

        int amount = 0;
        Type type = Type.SET;
        for (String arg : scriptEntry.getArguments()) {

            if (aH.matchesQuantity(arg) || aH.matchesInteger(arg)) {
                amount = aH.getIntegerFrom(arg);
            }

            else if (aH.matchesArg("SET, GIVE, TAKE", arg))
                type = Type.valueOf(arg.toUpperCase());

            else throw new InvalidArgumentsException(Messages.ERROR_UNKNOWN_ARGUMENT, arg);
        }

        scriptEntry.addObject("quantity", amount)
                .addObject("type", type);

    }

    
    @Override
    public void execute(ScriptEntry scriptEntry) throws CommandExecutionException {

        Type type = (Type) scriptEntry.getObject("type");
        Integer quantity = (Integer) scriptEntry.getObject("quantity");

        dB.report(name, aH.debugObj("Type", type.toString())
            + aH.debugObj("Quantity", quantity.toString())
            + aH.debugObj("Player", scriptEntry.getPlayer().getName()));

        switch (type) {
            case SET:
                scriptEntry.getPlayer().setTotalExperience(0);
                break;

            case GIVE:
                scriptEntry.getPlayer()
                        .setTotalExperience(scriptEntry.getPlayer().getTotalExperience() + quantity);
                break;

            case TAKE:
                scriptEntry.getPlayer()
                        .setTotalExperience(scriptEntry.getPlayer().getTotalExperience() - quantity);
                break;
        }

    }
}
