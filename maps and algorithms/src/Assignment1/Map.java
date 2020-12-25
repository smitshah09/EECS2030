/* PLEASE DO NOT MODIFY A SINGLE STATEMENT IN THE TEXT BELOW.
 READ THE FOLLOWING CAREFULLY AND FILL IN THE GAPS

I hereby declare that all the work that was required to
solve the following problem including designing the algorithms
and writing the code below, is solely my own and that I received
no help in creating this solution and I have not discussed my solution
with anybody. I affirm that I have read and understood
 the Senate Policy on Academic honesty at
https://secretariat-policies.info.yorku.ca/policies/academic-honesty-senate-policy-on/
and I am well aware of the seriousness of the matter and the penalties that I will face as a
result of committing plagiarism in this assignment.

BY FILLING THE GAPS,YOU ARE SIGNING THE ABOVE STATEMENTS.

Full Name: ZENITH PATEL
Student Number: 217517418
Course Section: SECTION E
*/
package Assignment1;
import java.util.*;
public class Map {
boolean [][] map;
private int row;
private int column;

public Map(int row, int column) {
this.row = row;
this.column = column;
}
/**
* @exception IllegalArgumentException if any of the precondition did not meet.
*/
public String getPath (int startRow, int startCol, int destRow, int destCol , String path) {


if (startRow > row || startCol > column || destRow > row || destCol > column || startRow < 0 || startCol < 0 || destRow < 0 || destCol < 0) {
throw new IllegalArgumentException();
}

if (startRow >= destRow && startCol >= destCol) {
return this.goSouthWest(startRow, startCol, destRow, destCol, path);
}
  else if (startRow >= destRow && startCol <= destCol) {
    return goSouthEast(startRow, startCol, destRow, destCol, path);
   }
  else if (startRow <= destRow && startCol <= destCol) {
  return goNorthEast(startRow, startCol, destRow, destCol, path);
  }
  else if (startRow <= destRow && startCol >= destCol) {
  return goNorthWest(startRow, startCol, destRow, destCol, path);
  }

return path;

}
private String goSouthWest (int startRow, int startCol, int destRow, int destCol , String path) {

if(startRow > destRow )
{
path += "(" + (startRow-1) + "," + startCol +") ";
path = this.goSouthWest(startRow-1, startCol, destRow, destCol, path);
}
else if(startCol > destCol)
{
path += "(" + startRow + "," + (startCol-1) + ") ";
path = this.goSouthWest(startRow, startCol-1, destRow, destCol, path);
}
return path;
}
private String goSouthEast (int startRow, int startCol, int destRow, int destCol , String path) {

if(startRow > destRow )
{
//startRow = startRow--;
path += "(" + (startRow-1) + "," + startCol +") ";
path = this.goSouthEast(startRow-1, startCol, destRow, destCol, path);
}
else if(startCol <  destCol)
{
startCol = startCol++;
path += "(" + destRow + "," + (startCol+1) + ") ";
path = this.goSouthEast(destRow, startCol+1, destRow, destCol, path);
}
return path;
}
private String goNorthEast (int startRow, int startCol, int destRow, int destCol , String path) {
if(startRow < destRow )
{
startRow = startRow+1;
path += "(" + startRow + "," + startCol +") ";
path = this.goNorthEast(startRow, startCol, destRow, destCol, path);
}
else if(startCol < destCol)
{
startCol = startCol+1;
path += "(" + startRow + "," + startCol + ") ";
path = this.goNorthEast(startRow, startCol, destRow, destCol, path);
}
return path;
}
private String goNorthWest (int startRow, int startCol, int destRow, int destCol , String path) {

if(startRow < destRow )
{
startRow = startRow+1;
path += "(" + startRow + "," + startCol +") ";
path = this.goNorthWest(startRow, startCol, destRow, destCol, path);
}
else if(startCol > destCol)
{
startCol = startCol-1;
path += "(" + startRow + "," + startCol + ") ";
path = this.goNorthWest(startRow, startCol, destRow, destCol, path);
}
return path;

}
public String findPath(int startRow, int startCol) {
String path = "(" + startRow + "," + startCol + ") ";
path = getPath(startRow,startCol,this.row-1,this.column-1,path);
return path;
}
}
// end of class
