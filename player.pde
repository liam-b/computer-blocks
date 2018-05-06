class Player {
  int selectedType;
  int selectedRotation;
  
  Player() {
    selectedType = CABLE;
    selectedRotation = 0;
  }
  
  void updateSelectedType() {
    if (key == '1') selectedType = EMPTY;
    if (key == '2') selectedType = CABLE;
    if (key == '3') selectedType = SOURCE;
    if (key == '4') selectedType = INVERTER;
    if (key == 'r') selectedRotation += 1;
    //if (key == '4') selectedType = TRIGGER;
    
    if (selectedRotation > 3) selectedRotation = 0;
    if (key == 'r') println(selectedRotation);
  }
}
