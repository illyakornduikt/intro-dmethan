package tile;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/res/maps/world01.txt");
    }

    public void getTileImage() {
        try {
            for (int i = 0; i < tile.length; i++) {
                tile[i] = new Tile(); // Защита от null
            }

            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png"));
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/earth.png"));
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree.png"));
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand.png"));
            tile[2].collision = true;
            tile[1].collision = true;
            tile[4].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadMap(String Filepath) {
        try {
            InputStream is = getClass().getResourceAsStream(Filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break; // Защита от null

                String[] numbers = line.split(" ");
                while (col < gp.maxWorldCol && col < numbers.length) { // Проверяем длину строки
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                 worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                 worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);}

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }
        }
    }

}

