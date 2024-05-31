# Monopoly

## Functionality table
|PRIORITY  | ENDPOINT | Client | Client | Server | Server |
|-|-|-|-|-|-|
| | | Visualize (HTML/CSS) | Consume API (JS) | Process request (API-Bridge) | Implement Game Rules (logic) |
| | **General Game and API info** | 100% | YES/NO | 100% | YES/NO |
| | GET / | ✓ | ✓ | ✓ | ✓ |
| MUSTHAVE | GET /tiles | ✓ | ✓ | ✓ | ✓ |
| MUSTHAVE | GET /tiles /{tileId} | ✓ | ✓ | ✓ | ✓ |
| | GET /chance | | | ✓ | ✓ |
| | GET /community-chest | | | ✓ | ✓ |
| | | | | | |
| | **Managing Games** | | | | |
| | DELETE /games | | | | |
| MUSTHAVE | GET /games | ✓ | ✓ | ✓ | ✓ |
| | Additional requirements: with filters | | ✓ | ✓ | ✓ |
| MUSTHAVE | POST /games | ✓ | ✓ | ✓ | ✓ |
| MUSTHAVE | POST /games /{gameId} /players | ✓ | ✓ | ✓ | ✓ |
| | | | | | |
| | **Info** | | | | | |
| | GET /games /dummy | | | ✓ | ✓ |
| MUSTHAVE | GET /games /{gameId} | ✓ | ✓ | ✓ | ✓ |
| | | | | | |
| | **Turn Management** | | | | |
| MUSTHAVE | POST /games /{gameId} /players /{playerName} /dice | ✓ | ✓ | ✓ | ✓ |
| | With jail | | | ✓ | ✓ |
| MUSTHAVE | POST /games /{gameId} /players /{playerName} /bankruptcy | ✓ | ✓ | ✓ | ✓ |
| | Decent distribution of assets | | | | |
| | | | | | |
| | **Improving property** | | | | |
| | POST /games /{gameId} /players /{playerName} /properties /{propertyName} /house | | | ✓ | ✓ |
| | DELETE /games /{gameId} /players /{playerName} /properties /{propertyName} /house | | | ✓ | ✓ |
| | POST /games /{gameId} /players /{playerName} /properties /{propertyName} /hotel | | | | |
| | DELETE /games /{gameId} /players /{playerName} /properties /{propertyName} /hotel | | | | |
| | | | | | |
| | **Interaction with another player** | | | | |
| MUSTHAVE | DELETE /games /{gameId} /players /{playerName} /properties /{propertyName} /visitors/ {debtorName} /rent | ✓ | ✓ | ✓ | ✓ |
| | With potential debt | | | | |
| | | | | | |
| | **Prison** | | | | |
| | POST /games /{gameId} /prison /{playerName} /fine | | | ✓ | ✓ |
| | POST /games /{gameId} /prison /{playerName} /free | | | ✓ | ✓ |
| | | | | |
| | **Auctions** | | | | |
| | GET /games /{gameId} /bank /auctions | | | | |
| | GET /games /{gameId} /bank /auctions /{propertyName} /bid | | | | |

## Known bugs
| Bug behaviour | How to reproduce | Why it hasn't been fixed |
|-|-|-|
| Roll dice button doesn't immediately disappear | Click on the button | Hard to fix and limited time. |

## Token scheme
Not implemented