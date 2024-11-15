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
package quests.Q00340_SubjugationOfLizardmen;

import org.l2jmobius.gameserver.enums.QuestSound;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.quest.Quest;
import org.l2jmobius.gameserver.model.quest.QuestState;
import org.l2jmobius.gameserver.model.quest.State;

public class Q00340_SubjugationOfLizardmen extends Quest
{
	// NPCs
	private static final int WEISZ = 30385;
	private static final int ADONIUS = 30375;
	private static final int LEVIAN = 30037;
	private static final int CHEST = 30989;
	// Items
	private static final int CARGO = 4255;
	private static final int HOLY = 4256;
	private static final int ROSARY = 4257;
	private static final int TOTEM = 4258;
	
	public Q00340_SubjugationOfLizardmen()
	{
		super(340);
		registerQuestItems(CARGO, HOLY, ROSARY, TOTEM);
		addStartNpc(WEISZ);
		addTalkId(WEISZ, ADONIUS, LEVIAN, CHEST);
		addKillId(20008, 20010, 20014, 20024, 20027, 20030, 25146);
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
		
		switch (event)
		{
			case "30385-03.htm":
			{
				st.startQuest();
				break;
			}
			case "30385-07.htm":
			{
				st.setCond(2, true);
				takeItems(player, CARGO, -1);
				break;
			}
			case "30385-09.htm":
			{
				takeItems(player, CARGO, -1);
				giveAdena(player, 4090, true);
				break;
			}
			case "30385-10.htm":
			{
				takeItems(player, CARGO, -1);
				giveAdena(player, 4090, true);
				st.exitQuest(true);
				break;
			}
			case "30375-02.htm":
			{
				st.setCond(3, true);
				break;
			}
			case "30037-02.htm":
			{
				st.setCond(5, true);
				break;
			}
			case "30989-02.htm":
			{
				st.setCond(6, true);
				giveItems(player, TOTEM, 1);
				break;
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
				htmltext = (player.getLevel() < 17) ? "30385-01.htm" : "30385-02.htm";
				break;
			}
			case State.STARTED:
			{
				final int cond = st.getCond();
				switch (npc.getId())
				{
					case WEISZ:
					{
						if (cond == 1)
						{
							htmltext = (getQuestItemsCount(player, CARGO) < 30) ? "30385-05.htm" : "30385-06.htm";
						}
						else if (cond == 2)
						{
							htmltext = "30385-11.htm";
						}
						else if (cond == 7)
						{
							htmltext = "30385-13.htm";
							giveAdena(player, 14700, true);
							st.exitQuest(false, true);
						}
						break;
					}
					case ADONIUS:
					{
						if (cond == 2)
						{
							htmltext = "30375-01.htm";
						}
						else if (cond == 3)
						{
							if (hasQuestItems(player, ROSARY, HOLY))
							{
								htmltext = "30375-04.htm";
								st.setCond(4, true);
								takeItems(player, HOLY, -1);
								takeItems(player, ROSARY, -1);
							}
							else
							{
								htmltext = "30375-03.htm";
							}
						}
						else if (cond == 4)
						{
							htmltext = "30375-05.htm";
						}
						break;
					}
					case LEVIAN:
					{
						if (cond == 4)
						{
							htmltext = "30037-01.htm";
						}
						else if (cond == 5)
						{
							htmltext = "30037-03.htm";
						}
						else if (cond == 6)
						{
							htmltext = "30037-04.htm";
							st.setCond(7, true);
							takeItems(player, TOTEM, -1);
						}
						else if (cond == 7)
						{
							htmltext = "30037-05.htm";
						}
						break;
					}
					case CHEST:
					{
						if (cond == 5)
						{
							htmltext = "30989-01.htm";
						}
						else
						{
							htmltext = "30989-03.htm";
						}
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				htmltext = getAlreadyCompletedMsg(player);
				break;
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(Npc npc, Player player, boolean isPet)
	{
		final QuestState st = getQuestState(player, false);
		if ((st == null) || !st.isStarted())
		{
			return null;
		}
		
		switch (npc.getId())
		{
			case 20008:
			{
				if (st.isCond(1) && (getQuestItemsCount(player, CARGO) < 30) && (getRandom(100) < 50))
				{
					giveItems(player, CARGO, 1);
					playSound(player, QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
				break;
			}
			case 20010:
			{
				if (st.isCond(1) && (getQuestItemsCount(player, CARGO) < 30) && (getRandom(100) < 52))
				{
					giveItems(player, CARGO, 1);
					playSound(player, QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
				break;
			}
			case 20014:
			{
				if (st.isCond(1) && (getQuestItemsCount(player, CARGO) < 30) && (getRandom(100) < 55))
				{
					giveItems(player, CARGO, 1);
					playSound(player, QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
				break;
			}
			case 20024:
			case 20027:
			case 20030:
			{
				if (st.isCond(3) && (getQuestItemsCount(player, HOLY) < 1))
				{
					if (getRandom(100) < 10)
					{
						giveItems(player, HOLY, 1);
						playSound(player, QuestSound.ITEMSOUND_QUEST_ITEMGET);
						if (getRandom(100) < 10)
						{
							giveItems(player, ROSARY, 1);
						}
					}
				}
				break;
			}
			case 25146:
			{
				addSpawn(CHEST, npc, false, 30000);
				break;
			}
		}
		return null;
	}
}
