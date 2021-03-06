/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.common;

import mage.constants.Zone;
import mage.MageObject;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DiesTriggeredAbility extends ZoneChangeTriggeredAbility {

    public DiesTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, Zone.GRAVEYARD, effect, "When {this} dies, ", optional);
    }

    public DiesTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DiesTriggeredAbility(DiesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, boolean checkLKI) {
        // check it was previously on battlefield
        Permanent before = (Permanent) game.getLastKnownInformation(sourceId, Zone.BATTLEFIELD);
        // check now it is in graveyard
        Zone after = game.getState().getZone(sourceId);
        return before != null && after != null && Zone.GRAVEYARD.match(after);
    }

    @Override
    public DiesTriggeredAbility copy() {
        return new DiesTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            for (Effect effect: getEffects()) {
                effect.setValue("diedPermanent", zEvent.getTarget());
            }
            return true;
        }
        return false;
    }

}
