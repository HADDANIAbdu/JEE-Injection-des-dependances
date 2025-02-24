package dao;

public class IDaoImpl implements IDao{

    @Override
    public double getData() {
        System.out.println("database Version !");
        double temperature = 26;
        return temperature;
    }
}
