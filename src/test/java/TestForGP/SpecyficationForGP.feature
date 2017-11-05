Feature: Logging in
#This is how background can be used to eliminate duplicate steps 

Background: 
   Given My Firefox Browser is open on William Hill Bet Page

#Scenario with AND 
Scenario Outline: 
   When I find English Premier Leage place to bet 
   And I place a bet for Home win with value "<value>" 
   Then Webside should return proper value of money to win
Examples:
| value |
| 0.05  |  
| 2     |
| 1.889 |
| -0.6  |

