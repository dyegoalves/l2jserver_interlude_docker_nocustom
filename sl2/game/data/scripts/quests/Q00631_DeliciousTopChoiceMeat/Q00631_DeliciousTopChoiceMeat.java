/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q00631_DeliciousTopChoiceMeat;

import java.util.HashMap;
import java.util.Map;

import org.l2jmobius.gameserver.enums.QuestSound;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.quest.Quest;
import org.l2jmobius.gameserver.model.quest.QuestState;
import org.l2jmobius.gameserver.model.quest.State;
import org.l2jmobius.gameserver.util.Util;

public class Q00631_DeliciousTopChoiceMeat extends Quest
{
	// NPC
	private static final int TUNATUN = 31537;
	// Item
	private static final int TOP_QUALITY_MEAT = 7546;
	// Drop chances
	private static final Map<Integer, Integer> CHANCES = new HashMap<>();
	static
	{
		CHANCES.put(21460, 601000);
		CHANCES.put(21461, 480000);
		CHANCES.put(21462, 447000);
		CHANCES.put(21463, 808000);
		CHANCES.put(21464, 447000);
		CHANCES.put(21465, 808000);
		CHANCES.put(21466, 447000);
		CHANCES.put(21467, 808000);
		CHANCES.put(21479, 477000);
		CHANCES.put(21480, 863000);
		CHANCES.put(21481, 477000);
		CHANCES.put(21482, 863000);
		CHANCES.put(21483, 477000);
		CHANCES.put(21484, 863000);
		CHANCES.put(21485, 477000);
		CHANCES.put(21486, 863000);
		CHANCES.put(21498, 509000);
		CHANCES.put(21499, 920000);
		CHANCES.put(21500, 509000);
		CHANCES.put(21501, 920000);
		CHANCES.put(21502, 509000);
		CHANCES.put(21503, 920000);
		CHANCES.put(21504, 509000);
		CHANCES.put(21505, 920000);
	}
	// Rewards
	private static final int[][] REWARDS =
	{
		// @formatter:off
		{4039, 15},
		{4043, 15},
		{4044, 15},
		{4040, 10},
		{4042, 10},
		{4041, 5}
		// @formatter:on
	};
	
	public Q00631_DeliciousTopChoiceMeat()
	{
		super(631);
		registerQuestItems(TOP_QUALITY_MEAT);
		addStartNpc(TUNATUN);
		addTalkId(TUNATUN);
		addKillId(CHANCES.keySet());
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		String htmltext = event;
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equals("31537-03.htm"))
		{
			if (player.getLevel() >= 65)
			{
				st.startQuest();
			}
			else
			{
				htmltext = "31537-02.htm";
				st.exitQuest(true);
			}
		}
		else if (Util.isDigit(event))
		{
			if (getQuestItemsCount(player, TOP_QUALITY_MEAT) >= 120)
			{
				htmltext = "31537-06.htm";
				takeItems(player, TOP_QUALITY_MEAT, -1);
				
				final int[] reward = REWARDS[Integer.parseInt(event)];
				rewardItems(player, reward[0], reward[1]);
				
				st.exitQuest(true, true);
			}
			else
			{
				st.setCond(1);
				htmltext = "31537-07.htm";
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(Npc npc, Player player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = getQuestState(player, true);
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				htmltext = "31537-01.htm";
				break;
			}
			case State.STARTED:
			{
				final int cond = st.getCond();
				if (cond == 1)
				{
					htmltext = "31537-03a.htm";
				}
				else if (cond == 2)
				{
					if (getQuestItemsCount(player, TOP_QUALITY_MEAT) >= 120)
					{
						htmltext = "31537-04.htm";
					}
					else
					{
						st.setCond(1);
						htmltext = "31537-03a.htm";
					}
				}
				break;
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(Npc npc, Player player, boolean isPet)
	{
		final QuestState qs = getRandomPartyMemberState(player, 1, 3, npc);
		if (qs == null)
		{
			return null;
		}
		final Player partyMember = qs.getPlayer();
		
		final QuestState st = getQuestState(partyMember, false);
		if (st == null)
		{
			return null;
		}
		
		if (getRandom(1000000) < CHANCES.get(npc.getId()))
		{
			giveItems(partyMember, TOP_QUALITY_MEAT, 1);
			if (getQuestItemsCount(partyMember, TOP_QUALITY_MEAT) < 120)
			{
				playSound(partyMember, QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
			else
			{
				st.setCond(2, true);
			}
		}
		
		return null;
	}
}
