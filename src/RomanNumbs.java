public enum RomanNumbs {
    I((byte) 1), II((byte) 2),III((byte) 3),IV((byte) 4),V((byte) 5),
    VI((byte) 6), VII((byte) 7),VIII((byte) 8),IX((byte) 9),X((byte) 10);

    private final byte arabic;
    RomanNumbs(byte value){
        this.arabic = value;
    }

    public byte getByte(){
        return arabic;
    }
}
