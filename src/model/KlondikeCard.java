package model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Marek
 */
public class KlondikeCard implements Serializable, Card {
  private Card.Color color;
  private int value;
  private boolean turnedFaceUp = false;
  
  public KlondikeCard(Card.Color c, int value) {
    this.color = c;
    this.value = value;
  }

  @Override
  public String toString() {
    String textValue;
    
    switch (this.value) {
      case 1:
        textValue = "a";
        break;
      case 11:
        textValue = "j";
        break;
      case 12:
        textValue = "q";
        break;
      case 13:
        textValue = "k";
        break;
      default:
        textValue = String.valueOf(value);
        break;
    }
    return textValue + color;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 61 * hash + Objects.hashCode(this.color);
    hash = 61 * hash + this.value;
    hash = 61 * hash + (this.turnedFaceUp ? 1 : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final KlondikeCard other = (KlondikeCard) obj;
    if (this.value != other.value) {
      return false;
    }
    if (this.color != other.color) {
      return false;
    }
    if (this.turnedFaceUp != other.turnedFaceUp) {
      return false;
    }
    return true;
  }
  
  @Override
  public Color color() {
    return this.color;
  }
  
  @Override
  public int compareValue(Card c) {
    return this.value - ((KlondikeCard) c).value;
  }
  
  @Override
  public boolean isTurnedFaceUp() {
    return this.turnedFaceUp;
  }
  
  @Override
  public boolean similarColorTo(Card c) {
    switch (((KlondikeCard) c).color) {
      case DIAMONDS:
      case HEARTS:
        return (this.color == Color.DIAMONDS || this.color == Color.HEARTS);
      case CLUBS:
      case SPADES:
        return (this.color == Color.CLUBS || this.color == Color.SPADES);
      default:
        return false;
    }
  }
  
  @Override
  public boolean turnFaceUp() {
    if (!this.isTurnedFaceUp()) {
      this.turnedFaceUp = true;
      return true;
    }
    return false;
  }
  
  @Override
  public boolean turnFaceDown() {
    if (this.isTurnedFaceUp()) {
      this.turnedFaceUp = false;
      return true;
    }
    return false;
  }
  
  @Override
  public int value() {
    return this.value;
  }
}
