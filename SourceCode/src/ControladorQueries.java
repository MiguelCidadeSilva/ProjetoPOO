import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;

public class ControladorQueries {
    private ViewQueries viewQ;
    private App app;

    ControladorQueries(App appQ) {
        this.viewQ = new ViewQueries();
        this.app = appQ;
    }

    public String queryMF() {
        String r = "";
        r = app.queryMaiorFornecedor().toString();
        return r;
    }

    public String queryMC() {
        String r = app.queryMaiorConsumidor().toString();
        return r;
    }

    public String queryMCDates(LocalDate d1, LocalDate d2) {
        String r = app.queryMaioresConsumidores(d1, d2).toString();
        return r;
    }


    public String consultaDados() throws ValorNegativoException, NullPointerException {
        String r = "";
        int n = viewQ.consultarOsDados();
        switch (n) {
            case 1: //
                r = queryMF();
                break;
            case 2:
                r = queryMC();
                break;
            case 3:
                LocalDate d1 = viewQ.getDate();
                LocalDate d2 = viewQ.getDate();
                r = queryMCDates(d1, d2);

            case 4:
                int numero = viewQ.inserInteger();
                app.avancaDias(numero);

            default:
                break;
        }
        viewQ.success();
        return r;
    }

    public void selecionouConsultaDados() {
        try {
            int n = viewQ.atividadeConsultar();
            if (n == 1)
                this.consultaDados();
        } catch (ValorNegativoException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}

