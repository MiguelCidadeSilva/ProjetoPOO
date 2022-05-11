import java.util.Objects;
import java.util.function.Predicate;

public class ControladorDevices {
    private ViewDevices view;
    private App app;

    ControladorDevices(App app){
        this.app = app;
        this.view = new ViewDevices();
    }
    public void parseSmartBulb(String string) throws ValorNegativoException {
        String[] campos = string.split(",");
        int tone;
        switch (campos[0])
        {
            case "Warm":
                tone = SmartBulb.WARM;
                break;
            case "Neutral":
                tone = SmartBulb.NEUTRAL;
                break;
            default:
                tone = SmartBulb.COLD;
                break;
        }
        double tamanho = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        app.addSmartBulbP(true,tone,tamanho,consumo);
    }
    public void parseSmartCamera(String string) throws ValorNegativoException {
        String[] campos = string.split(",");
        int x, y;
        campos[0] = campos[0].substring(1,campos[0].length() - 1);
        String []resolucao = campos[0].split("x");
        x = Integer.parseInt(resolucao[0]);
        y = Integer.parseInt(resolucao[1]);
        double tamanho = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        app.addSmartCameraP(true,consumo,x,y,tamanho);
    }
    public void parseSmartSpeaker(String string) throws ValorNegativoException, ValorExcedeMaximoException {
        String[] campos = string.split(",");
        int volume = Integer.parseInt(campos[0]);
        double consumo = Double.parseDouble(campos[3]);
        app.addSmartSpeakerP(true,volume,campos[1],campos[2],consumo);
    }
    public void addDevice() throws ValorNegativoException, ValorExcedeMaximoException {
        int option = view.getDevice(); // 1 -> smartbulb, 2 -> smartcamera, 3 -> smartspeaker
        boolean on = view.getOn();
        double consumo = view.getConsumo();
        switch(option)
        {
            case 1:
                int tone = view.getTone();
                double dimensao = view.getDimensao();
                app.addSmartBulb(on,tone,dimensao,consumo);
                view.sucess();
                break;
            case 2:
                String[] res = view.getResolucao().split("x");
                double tamanho = view.getTamanho();
                app.addSmartCamera(on,consumo,Integer.parseInt(res[0]),Integer.parseInt(res[1]),tamanho);
                view.sucess();
                break;
            case 3:
                int volume = view.getVolume();
                String canal = view.getCanal();
                String marca = view.getMarca();
                app.addSmartSpeaker(on,volume,canal,marca,consumo);
                view.sucess();
                break;
            default:
                break;
        }
    }
    public int numberDevices()
    {
        return app.numberDevices();
    }

    public void addSmartBulb(boolean on, double consumo, int tone, double dimensao) throws ValorNegativoException {
        app.addSmartBulb(on, tone, dimensao, consumo);
        view.sucess();
    }
    public void addSmartCamera(boolean on, double consumo, String resolucao,double tamanho) throws ValorNegativoException {
        String[] res = resolucao.split("x");
        app.addSmartCamera(on, consumo, Integer.parseInt(res[0]), Integer.parseInt(res[1]), tamanho);
        view.sucess();
    }
    public void addSmartSpeaker(boolean on, double consumo, int volume, String canal, String marca) throws ValorNegativoException, ValorExcedeMaximoException {
        app.addSmartSpeaker(on,volume,canal,marca,consumo);
        view.sucess();
    }
    public Predicate<SmartDevices> devicesPredicate(){
        int n = view.predicates();
        Predicate<SmartDevices> r;
        switch (n){
            case 2:
                r = p ->(p.getConsumo() > view.insertValue());
                break;
            case 3:
                r = p ->(p.getConsumo() < view.insertValue());
                break;
            case 4:
                r = SmartDevices::isOn;
                break;
            case 5:
                r = p ->(!p.isOn());
                break;
            case 6:
                r = p ->(p instanceof SmartCamera);
                break;
            case 7:
                r = p -> (p instanceof SmartBulb);
                break;
            case 8:
                r = p -> (p instanceof SmartSpeaker);
                break;
            default:
                r = Objects::nonNull;
                break;
        }
        return r;
    }
    public void consultarDados() {
        Predicate<SmartDevices> p = this.devicesPredicate();
        String r = "";
        r = app.consultaDevice(p).toString();
        view.print(r);
    }

    private Predicate<SmartDevices> devicesPredicateChange(){
        int n = view.predicates();
        Predicate<SmartDevices> r;
        switch (n){
            case 2:
                r = p ->(p.getConsumo() > view.insertValue());
                break;
            case 3:
                r = p ->(p.getConsumo() < view.insertValue());
                break;
            case 4:
                r = SmartDevices::isOn;
                break;
            case 5:
                r = p ->(!p.isOn());
                break;

            default:
                r = Objects::nonNull;
                break;
        }
        return r;
    }



    public void changeDadosDevice() throws ValorNegativoException, NullPointerException, ValorExcedeMaximoException {
        //mudar - on, consumo, tone, volume e canal
        int mudaestado = view.mudaEstado();
        switch(mudaestado){
            case 1:
                Predicate<SmartDevices> r = this.devicesPredicate();
                int onoffconsumo = view.onOffConsumo();
                switch (onoffconsumo){
                    case 1:
                        app.setDevicesOnOff(r,true);
                        break;
                    case 2:
                        app.setDevicesOnOff(r,false);
                        break;
                    case 3:
                        app.setConsumoDevices(r,view.insertValue());
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                Predicate<SmartDevices> r2 = this.devicesPredicateChange();
                int tom = (int) view.insertValue();
                app.changeToneDevices(r2,tom);
                break;
            case 3:
                Predicate<SmartDevices> r3 = this.devicesPredicateChange();
                int canalvolume = view.canalVolume();
                if(canalvolume == 1){
                    String canal = view.insertString();
                    app.changeCanalDevices(r3,canal);
                }
                else if (canalvolume == 2){
                    int volume = (int) view.insertValue();
                    app.changeVolumeDevices(r3,volume);
                }
                break;
            default:
                break;
        }
    }
}


