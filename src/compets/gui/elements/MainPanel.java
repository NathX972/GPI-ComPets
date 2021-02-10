package compets.gui.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

import compets.config.GuiConfiguration;
import compets.engine.data.animal.Animal;
import compets.engine.data.map.Box;
import compets.engine.data.map.EmptyBox;
import compets.engine.data.map.Map;
import compets.engine.data.map.Position;
import compets.engine.data.map.Prop;
import compets.engine.data.map.Rectangle;
import compets.engine.data.map.Wall;
import compets.engine.data.map.item.BadItem;
import compets.engine.data.map.item.GoodItem;
import compets.engine.data.map.item.NeutralItem;
import compets.engine.process.AnimalManager;
import compets.engine.process.visitor.BoxVisitor;
import compets.gui.ColorConstants;
import compets.gui.management.PaintBoxVisitor;

public class MainPanel extends JPanel {

	private MainGui context;
	
	private int boxWidth;
	private int boxHeight;

	private PaintBoxVisitor paintVisitor = new PaintBoxVisitor();
	
	public MainPanel(MainGui context) {
		this.context = context;
		setLayout(new BorderLayout());
		setPreferredSize(MainGui.MAIN_PANEL_DIMENSION);
		setBackground(Color.BLACK);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Map map = context.getMap();
		Box[][] mapArray = map.getMap();

		
		int numberOfLines = mapArray.length;
		int numberOfColumns = mapArray[0].length;
		boxWidth = getWidth() / numberOfLines;
		boxHeight = getHeight() / numberOfColumns;

		drawBoxes(g, mapArray);
		ArrayList<Prop> props = map.getProps();
		drawProps(g, props);
		drawAnimal(g);

	}

	private void drawProps(Graphics g, ArrayList<Prop> props) {
		for (Prop prop : props) {
			Rectangle rectangle = prop.getRectangle();
			Image image = prop.getImage();
			Position position = rectangle.getPosition();
			int propWidth = rectangle.getWidth() * boxWidth;
			int propHeight = rectangle.getHeight() * boxHeight;
			int propPositionX = position.getX();
			int propPositionY = position.getY();
			g.drawImage(image, propPositionX * boxWidth, propPositionY * boxHeight, propWidth, propHeight, null);
		}
	}

	private void drawBoxes(Graphics g, Box[][] mapArray) {
		int numberOfLines = mapArray.length;
		int numberOfColumns = mapArray[0].length;
		paintVisitor.setGraphics(g);
		Rectangle rectangle = new Rectangle(null, boxWidth, boxHeight);
		for (int line = 0; line < numberOfColumns; line++) {
			for (int col = 0; col < numberOfLines; col++) {
				Box currentBox = mapArray[line][col];
				Position position = currentBox.getPosition();
				rectangle.setPosition(position);
				paintVisitor.setRectangle(rectangle);
				currentBox.accept(paintVisitor);
			}
		}
	}

	private void drawAnimal(Graphics g) {
		AnimalManager animalManager = context.getAnimalManager();
		Animal animal = animalManager.getAnimal();
		Position position = animal.getPosition();
		g.setColor(ColorConstants.ANIMAL_COLOR);
		g.fillOval(position.getX() * boxWidth, position.getY() * boxHeight, boxWidth, boxHeight);
	}
}
