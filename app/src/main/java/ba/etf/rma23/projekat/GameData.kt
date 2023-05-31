package ba.etf.rma23.projekat

class GameData {
    companion object{
        fun getAll():List<Game>{            return listOf(
                Game(1,"Genshin Impact","iOS, Android", "September 28, 2020",9.0,"genshinimpact","Teen","miHoYo", "miHoYo","Action role-playing","Genshin Impact is an open-world action RPG that takes place in the magical land of Teyvat. Players collect and control characters with elemental abilities to explore and fight against enemies.",
                    (listOf(
                        UserReview("user134",1499070300000L,"Amazing game. Beautiful world, engaging story, and satisfying combat."),
                        UserReview("user252",1499070300001L,"Stunning graphics and fun gameplay."),
                        UserReview("user33",1499070300002L,"Huge world, but can feel overwhelming. Gacha system can be frustrating."),
                        UserRating("user431",1499070300003L,4.2),
                        UserReview("user32425",1499070300004L,"Captivating story and addictive gacha system. Great game overall.")
                    ))
                ),
                Game(2,"Among Us","iOS, Android", "June 15, 2018",8.0,"amongus","Everyone","InnerSloth", "InnerSloth","Social deduction","Among Us is a multiplayer game where players work together on a spaceship, but some players are secretly imposters trying to sabotage the mission. Players must vote on who they think the imposters are.",
                    (listOf(
                        UserRating("user4",1499070300003L,1.1),
                        UserReview("user132",1499070300000L,"Simple but fun with friends. Highly recommend!"),
                        UserReview("user2",1499070300001L,"Addictive and engaging, but watch out for hackers."),
                        UserReview("user3",1499070300002L,"Easy to pick up, hard to master. Needs more maps."),
                        UserReview("user5",1499070300004L,"One of the best mobile games. Deception and catching impostors is so much fun.")
                    ))
                ),
                Game(3,"Pokemon Go","iOS, Android", "July 6, 2016",8.0,"pokemongo","Everyone","Niantic", "Niantic","Augmented reality","Pokémon Go is a mobile game that uses real-world locations to allow players to catch Pokémon, battle at gyms, and interact with other trainers.",
                    (listOf(
                        UserReview("user132",1499070300000L,"The game's use of augmented reality technology to bring Pokemon to life is impressive,"),
                        UserRating("user4",1499070300003L,3.1),
                        UserReview("user2",1499070300001L,"One of the best features of the game is its social aspect."),
                        UserReview("user3",1499070300002L,"The graphics and sound effects are charming."),
                        UserReview("user5",1499070300004L,"The game's constant updates and events keep things fresh and exciting.")
                    ))
                ),
                Game(4,"Clash Royale","iOS, Android", "March 2, 2016",8.0,"clashroyale","Everyone 10+","Supercell", "Supercell","Strategy","Clash Royale is a real-time strategy game where players collect and upgrade cards featuring Clash of Clans troops, spells, and defenses. Players battle against other players in 1v1 or 2v2 matches.",
                    (listOf(
                        UserReview("user132",1499070300000L,"Fast-paced and addictive card game."),
                        UserReview("user2",1499070300001L,"Easy to understand gameplay with depth and strategy."),
                        UserReview("user3",1499070300002L,"Social aspect with clans and wars is great."),
                        UserRating("user4",1499070300003L,3.1),
                        UserReview("user5",1499070300004L,"Top-notch graphics and sound effects.")
                    ))
                ),
                Game(5,"PUBG Mobile","iOS, Android", "March 19, 2018",9.0,"pubgmobile","Teen","PUBG Corporation", "Tencent Games","Battle royale","PUBG Mobile is a mobile version of the popular battle royale game. Players parachute onto an island, gather weapons and supplies, and try to be the last player or team standing.",
                    (listOf(
                        UserReview("user132",1499070300000L,"Thrilling battle royale game with realistic graphics!"),
                        UserReview("user2",1499070300001L,"Addictive and engaging, but watch out for hackers."),
                        UserReview("user3",1499070300002L,"Easy to pick up, hard to master. Needs more maps."),
                        UserRating("user4",1499070300003L,3.1),
                        UserReview("user5",1499070300004L,"Intense gameplay with a variety of weapons and vehicles.")
                    ))
                ),
                Game(6,"Brawl Stars","iOS, Android", "December 12, 2018",9.0,"brawlstars","Everyone 10+","Supercell", "Supercell","team-based shooter","Brawl Stars is a multiplayer game where players battle in different game modes and collect brawlers, each with their unique abilities.",
                    (listOf(
                        UserReview("user132",1499070300000L,"Simple but fun with friends. Highly recommend!"),
                        UserReview("user2",1499070300001L,"Great social aspect with team play and voice chat."),
                        UserReview("user3",1499070300002L,"Controls can be difficult for beginners."),
                        UserRating("user4",1499070300003L,3.1),
                        UserReview("user5",1499070300004L,"Some players have reported encountering cheaters.")
                    ))
                ),
                Game(7,"Monument Valley 2","iOS, Android", "June 5 2017",9.2,"monumentvalley2","Everyone","ustwo games", "ustwo games","Puzzle","Monument Valley 2 is a puzzle game where players guide a mother and her child through various optical illusion-based levels. The game features beautiful graphics and a captivating story.",
                    (listOf(
                        UserReview("user132",1499070300000L,"Stunningly beautiful and immersive puzzle game!"),
                        UserReview("user2",1499070300001L,"Unique and creative level design with mind-bending puzzles."),
                        UserReview("user3",1499070300002L,"Relaxing soundtrack and calming atmosphere."),
                        UserRating("user4",1499070300003L,3.1),
                        UserReview("user5",1499070300004L,"The emotional impact of the story is undeniable, and it will stay with you long after you finish the game.")
                    ))
                ),
                Game(8,"Legends","iOS, Android", "July 25, 2018",8.0,"legends","Everyone 10+","Gameloft Barcelona", "Gameloft","Racing","Legends is a racing game with over 70 cars from top manufacturers. Players can race in different modes and compete against other players in multiplayer matches.",
                    (listOf(
                        UserReview("user132",1499070300000L,"Simple but fun with friends. Highly recommend!"),
                        UserReview("user2",1499070300001L,"Addictive and engaging, but watch out for hackers."),
                        UserReview("user3",1499070300002L,"Easy to pick up, hard to master. Needs more maps."),
                        UserRating("user4",1499070300003L,3.1),
                        UserReview("user5",1499070300004L,"The attention to detail is staggering, and the world feels alive and vibrant.")
                    ))
                ),
                Game(9,"Fire Emblem Heroes","iOS, Android", "February 2 2017",8.0,"fireemblemheroes","Teen","Intelligent Systems", "Nintendo","Tactical role-playing","Fire Emblem Heroes is a mobile game where players collect and control heroes from the Fire Emblem series. Players can battle in story mode or compete against other players in arena matches.",
                    (listOf(
                        UserReview("user132",1499070300000L,"Overrated!"),
                        UserReview("user2",1499070300001L,"Very fun and easy to master."),
                        UserReview("user3",1499070300002L,"It's repetitive."),
                        UserRating("user4",1499070300003L,3.1),
                        UserReview("user5",1499070300004L,"One of the best mobile games. Deception and catching impostors is so much fun.")
                    ))
                ),
                Game(10,"Reigns","iOS, Android", "August 11, 2016",8.0,"reigns","Teen","Nerial", "Devolver Digital","Strategy","Reigns is a game where players make decisions as a monarch, swiping left or right to accept or reject proposals. Players must balance their relationships with different factions and keep their kingdom stable.",
                    (listOf(
                        UserReview("user132",1499070300000L,"Overly complicated!"),
                        UserReview("user2",1499070300001L,"One of the best games I have ever played."),
                        UserReview("user3",1499070300002L,"Needs more levels."), UserRating("user4",1499070300003L,3.1),
                        UserReview("user5",1499070300004L,"This game is a relaxing and charming escape from reality.")
                    ))
                )

            )
        }
        fun getDetails(title:String): Game? {
            val games = getAll()
            return games.find { game->title == game.title }
        }



    }
}