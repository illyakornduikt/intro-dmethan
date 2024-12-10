package main;

import entity.EntityVariables;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends EntityVariables {
    GamePanel gp;
    KeyHandler keyH;

    public final  int screenX;
    public  final  int screenY;
    public int hasKey = 0;

    // Constructor
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8,16,32,32);
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;


        setDefaultValues();
        getPlayerImage();
    }

    // Set default values for player
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        spriteNum = 1;
        spriteCounter = 0;
    }

    // Load player images for different directions
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update player position based on key presses and sprite animation
    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true ||
                keyH.rightPressed){
            if (keyH.upPressed) {
                direction = "up";

            } else if (keyH.downPressed) {
                direction = "down";

            } else if (keyH.leftPressed) {
                direction = "left";

            } else if (keyH.rightPressed) {
                direction = "right";

            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);
            if (collisionOn == false){
                switch (direction){
                    case "up":worldY -= speed;break;
                    case "down":worldY += speed;break;
                    case "left":worldX -= speed;break;
                    case "right":worldX += speed;break;
                }
            }

        // Sprite animation logic
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        }
    }

    public void pickUpObject(int i){
    if (i != 999){
    String objectName = gp.obj[i].name;
    switch (objectName) {
        case "Key":
            gp.playSE(1);
            hasKey++;
            gp.obj[i] = null;
            gp.ui.showMessage("You got a key!");
            break;
        case "Door":
            if (hasKey > 0) {
                gp.playSE(3);
                gp.obj[i] = null;
                hasKey--;
                gp.ui.showMessage("You opened the door!");
            }else {
                gp.ui.showMessage("You need a key!");
            }

            break;
        case "Boots":
            gp.playSE(2);
            speed += 2;
            gp.obj[i]=null;
            gp.ui.showMessage("Speed up!");
            break;
        case "Chest":
            gp.ui.gameFinished = true;
            gp.stopMusic();
            gp.playSE(4);
            break;
    }
    }
    }

    // Draw the player on the screen with animation
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
        }

        // Draw the player's image
        if (image != null) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
