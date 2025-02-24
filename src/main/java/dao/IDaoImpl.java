package dao;

import org.springframework.stereotype.Repository;

@Repository
public class IDaoImpl implements IDao{

    @Override
    public double getData() {
        System.out.println("database Version !");
        double temperature = 86;
        return temperature;
    }
}
