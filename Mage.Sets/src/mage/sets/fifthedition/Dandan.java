/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.fifthedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerControlsIslandPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author KholdFuzion

 */
public class Dandan extends CardImpl {

    public Dandan(UUID ownerId) {
        super(ownerId, 79, "Dandan", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.expansionSetCode = "5ED";
        this.subtype.add("Fish");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Dandan can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent("Island","an Island"))));
        
        // When you control no Islands, sacrifice Dandan.
        this.addAbility(new DandanTriggeredAbility());
    }

    public Dandan(final Dandan card) {
        super(card);
    }

    @Override
    public Dandan copy() {
        return new Dandan(this);
    }
}

class DandanTriggeredAbility extends StateTriggeredAbility {

    public DandanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public DandanTriggeredAbility(final DandanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DandanTriggeredAbility copy() {
        return new DandanTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return (game.getBattlefield().countAll(ControllerControlsIslandPredicate.filter, controllerId, game) == 0);
    }

    @Override
    public String getRule() {
        return "When you control no Islands, sacrifice {this}.";
    }
}