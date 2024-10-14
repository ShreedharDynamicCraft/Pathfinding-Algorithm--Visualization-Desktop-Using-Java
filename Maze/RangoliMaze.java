// Source code is decompiled from a .class file using FernFlower decompiler.
package Maze;

import Backend.Node;
import GraphicalUI.Panel;
import Helper.MazeAlgo;

import java.util.Random;

public class RangoliMaze extends MazeAlgo {
   private static final int HORIZONTAL = 1;
   private static final int VERTICAL = 2;
   private static final int S = 1;
   private static final int E = 2;
   private final Random rand = new Random();

   public RangoliMaze(Panel var1) {
      super(var1);
   }

   protected void generateMaze() {
      for(int var1 = 0; var1 < Panel.numRow; ++var1) {
         for(int var2 = 0; var2 < Panel.numCol; ++var2) {
            Node var3 = Panel.nodesGrid[var1][var2];
            var3.addNeighbors(true);
            if (var2 > 0) {
               var3.getWall().setLeftWall(false);
            }

            if (var2 < Panel.numCol - 1) {
               var3.getWall().setRightWall(false);
            }

            if (var1 > 0) {
               var3.getWall().setTopWall(false);
            }

            if (var1 < Panel.numRow - 1) {
               var3.getWall().setBottomWall(false);
            }
         }
      }

      this.divide(Panel.nodesGrid, 0, 0, Panel.numCol, Panel.numRow, this.chooseOrientation(Panel.numCol, Panel.numRow));
   }

   private void divide(Node[][] var1, int var2, int var3, int var4, int var5, int var6) {
      if (var4 >= 2 && var5 >= 2) {
         boolean var7 = var6 == 1;
         int var8 = var2 + (var7 ? 0 : this.rand.nextInt(var4 - 1));
         int var9 = var3 + (var7 ? this.rand.nextInt(var5 - 1) : 0);
         int var10 = var8 + (var7 ? this.rand.nextInt(var4) : 0);
         int var11 = var9 + (var7 ? 0 : this.rand.nextInt(var5));
         int var12 = var7 ? 1 : 0;
         int var13 = var7 ? 0 : 1;
         int var14 = var7 ? var4 : var5;
         int var15 = var7 ? 1 : 2;

         int var16;
         for(var16 = 0; var16 < var14; ++var16) {
            if (var8 != var10 || var9 != var11) {
               Node var17 = var1[var9][var8];
               if (var15 == 1) {
                  var17.getWall().setBottomWall(true);
                  if (var17.getBottomNeighbor() != null) {
                     var17.getBottomNeighbor().getWall().setTopWall(true);
                  }
               } else {
                  var17.getWall().setRightWall(true);
                  if (var17.getRightNeighbor() != null) {
                     var17.getRightNeighbor().getWall().setLeftWall(true);
                  }
               }

               this.process();
            }

            var8 += var12;
            var9 += var13;
         }

         int var18 = var7 ? var4 : var8 - var2 + 1;
         int var19 = var7 ? var9 - var3 + 1 : var5;
         this.divide(var1, var2, var3, var18, var19, this.chooseOrientation(var18, var19));
         var16 = var7 ? var2 : var8 + 1;
         int var20 = var7 ? var9 + 1 : var3;
         var18 = var7 ? var4 : var2 + var4 - var8 - 1;
         var19 = var7 ? var3 + var5 - var9 - 1 : var5;
         this.divide(var1, var16, var20, var18, var19, this.chooseOrientation(var18, var19));
      }
   }

   private int chooseOrientation(int var1, int var2) {
      if (var1 < var2) {
         return 1;
      } else {
         return var2 < var1 ? 2 : this.rand.nextInt(2) + 1;
      }
   }
}
