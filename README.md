**# Checkers Project**

_Buliga Nicu, Luca Radu, anul 2, grupa B1._

#Checkers Table:
![Checkers Table](/checkersTable.PNG)

#Client move:
![Checkers Table Move](/checkersTable_move.PNG)

#Final game:
![Checkers Table Move](/finalMoves.PNG)

#Game Over:
![Checkers Table Move](/gameOver.PNG)

Aplicatia noastra este formata dintr-un server si client.
Serverul are grija de conexiuni si distribuie clientii in jocuri.
Clientul isi poate alege daca sa joace impotriva unui jucator real sau a unui bot.

Pentru 2 clienti conectati care au ales sa joace impotriva unui jucator real, se va crea un nou thread care va avea grija de comunicarea dintre clienti
si de desfasurarea jocului propriu-zis.

Daca un client va alege optiunea **AI** atunci pentru acesta se va crea un nou thread in server care va contine algoritmul bot-ului, Minimax in cazul nostru.

Clientul si Serverul comunica prin protocoale definite de noi: 
* start "myId"
* turn "opponentMove"
* GameOver "gameStatus"
* test

Pentru interfata grafica am utilizat SWING.

