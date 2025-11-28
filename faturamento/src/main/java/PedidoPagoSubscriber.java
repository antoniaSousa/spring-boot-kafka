import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.antoniasousa.icompras.faturamento.GeradorNotaFiscalService;
import io.github.antoniasousa.icompras.pedidos.controller.mappers.PedidoMapper;
import io.github.antoniasousa.icompras.pedidos.model.Pedido;
import io.github.antoniasousa.icompras.pedidos.publisher.representation.DetalhesPedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoPagoSubscriber {

    private final ObjectMapper mapper;
    private final GeradorNotaFiscalService service;
    private final PedidoMapper pedidoMapper;

    @KafkaListener(groupId = "icompras-faturamento",
    topics =  "${icompras.config.kafka.topis.pedidos-pagos}")

    public void lister(String json) {
        try {
            log.info("REcebendo pedido para faturamento: {}", json);
            var representation = mapper.readValue(json, DetalhesPedidoRepresentation.class);
            Pedido pedido = pedidoMapper.map(representation);
            service.gerar(pedido);
        } catch (Exception e){
            log.error("Erro ao receber pedido para faturamento");
        }
    }

}
