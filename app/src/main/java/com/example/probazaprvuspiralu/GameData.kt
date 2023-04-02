package com.example.probazaprvuspiralu

import java.net.URL
import java.time.LocalDate

class GameData {
    companion object{
        fun getAll():List<Game>{
            return listOf(
                Game("Genshin Impact","iOS, Android", "September 28, 2020",9.0,"genshinimpact","Teen","miHoYo", "miHoYo","Action role-playing","Genshin Impact is an open-world action RPG that takes place in the magical land of Teyvat. Players collect and control characters with elemental abilities to explore and fight against enemies.",
                    (listOf(UserReview("user134",System.currentTimeMillis(),"Amazing game. Beautiful world, engaging story, and satisfying combat."),UserReview("user252",System.currentTimeMillis(),"Stunning graphics and fun gameplay. Great for playing with friends."),UserReview("user33",System.currentTimeMillis(),"Huge world, but can feel overwhelming. Gacha system can be frustrating."),
                        UserRating("user431",System.currentTimeMillis(),3.0),UserReview("user32425",System.currentTimeMillis(),"Captivating story and addictive gacha system. Great game overall.")
                    ))
                ),
                Game("Among Us","iOS, Android", "June 15, 2018",4.3,"amongus","Everyone","InnerSloth", "InnerSloth","Social deduction","Among Us is a multiplayer game where players work together on a spaceship, but some players are secretly imposters trying to sabotage the mission. Players must vote on who they think the imposters are.",
                    (listOf(UserReview("user132",System.currentTimeMillis(),"Simple but fun with friends. Highly recommend!"),UserReview("user2",System.currentTimeMillis(),"Addictive and engaging, but watch out for hackers."),UserReview("user3",System.currentTimeMillis(),"Easy to pick up, hard to master. Needs more maps."),
                        UserRating("user4",System.currentTimeMillis(),5.0),UserReview("user5",System.currentTimeMillis(),"One of the best mobile games. Deception and catching impostors is so much fun. More character customization would be great.")
                    ))
                ),
                Game("Pokemon Go","iOS, Android", "July 6, 2016",2.0,"pokemongo","Everyone","Niantic", "Niantic","Augmented reality","Pokémon Go is a mobile game that uses real-world locations to allow players to catch Pokémon, battle at gyms, and interact with other trainers.",
                    emptyList()
                ),
                Game("Clash Royale","iOS, Android", "March 2, 2016",2.3,"clashroyale","Everyone 10+","Supercell", "Supercell","Strategy","Clash Royale is a real-time strategy game where players collect and upgrade cards featuring Clash of Clans troops, spells, and defenses. Players battle against other players in 1v1 or 2v2 matches.",
                    emptyList()
                ),
                Game("PUBG Mobile","iOS, Android", "March 19, 2018",1.0,"pubgmobile","Teen","PUBG Corporation", "Tencent Games","Battle royale","PUBG Mobile is a mobile version of the popular battle royale game. Players parachute onto an island, gather weapons and supplies, and try to be the last player or team standing.",
                    emptyList()
                ),
                Game("Brawl Stars","iOS, Android", "December 12, 2018",4.5,"brawlstars","Everyone 10+","Supercell", "Supercell","team-based shooter","Brawl Stars is a multiplayer game where players battle in different game modes and collect brawlers, each with their unique abilities.",
                    emptyList()
                ),
                Game("Monument Valley 2","iOS, Android", "June 5 2017",5.0,"monumentvalley2","Everyone","ustwo games", "ustwo games","Puzzle","Monument Valley 2 is a puzzle game where players guide a mother and her child through various optical illusion-based levels. The game features beautiful graphics and a captivating story.",
                    emptyList()
                ),
                Game("Legends","iOS, Android", "July 25, 2018",1.2,"legends","Everyone 10+","Gameloft Barcelona", "Gameloft","Racing","Legends is a racing game with over 70 cars from top manufacturers. Players can race in different modes and compete against other players in multiplayer matches.",
                    emptyList()
                ),
                Game("Fire Emblem Heroes","iOS, Android", "February 2 2017",3.0,"fireemblemheroes","Teen","Intelligent Systems", "Nintendo","Tactical role-playing","Fire Emblem Heroes is a mobile game where players collect and control heroes from the Fire Emblem series. Players can battle in story mode or compete against other players in arena matches.",
                    emptyList()
                ),
                Game("Reigns","iOS, Android", "August 11, 2016",2.0,"reigns","Teen","Nerial", "Devolver Digital","Strategy","Reigns is a game where players make decisions as a monarch, swiping left or right to accept or reject proposals. Players must balance their relationships with different factions and keep their kingdom stable.",
                   emptyList()
                )

            )
        }
        fun getDetails(title:String): Game? {
            val games = getAll()
            return games.find { game->title == game.title }
        }



    }
}