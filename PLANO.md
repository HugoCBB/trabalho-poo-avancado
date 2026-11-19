# Plano de Execução — Trabalho POO Avançada (2ª Unidade)

Nov 18, 2026 · @Hugo

## O que o professor exige

Uma aplicação Java funcional, feita no Eclipse, que use **6 padrões de projeto integrados** (2 criacionais, 2 estruturais, 2 comportamentais), respeite GRASP e os fundamentos de POO. Vale 10,0 e substitui a média da 2ª unidade. Fonte: [Atividade Avaliativa – POA – Segunda Unidade](https://docs.google.com/document/d/1vZWm8OqsAeMeUN7f50P76lzT_uelSO87vti0qFYz2-E/edit) (Prof. Everton Pires).

| Item | O que precisa ter |
| --- | --- |
| Aplicação | Objetivo claro, fluxo coerente, funcionalidades executáveis pelo professor. Nada de exemplos soltos de padrões. |
| Padrões | 2 + 2 + 2, cada um resolvendo um problema real do sistema e justificado (onde, por quê, que ganho traz). |
| GRASP | Baixo acoplamento, alta coesão, Controller, Creator, Information Expert visíveis no design. |
| POO | Encapsulamento, herança e composição, polimorfismo, interfaces e classes abstratas, sobrescrita e sobrecarga. |
| Ambiente | Java no Eclipse, pacotes por responsabilidade, classe `Main`, importa e roda sem ajuste. |
| Entregáveis | (A) projeto Eclipse completo, (B) código organizado, (C) documentação técnica, (D) diagrama de classes UML. |
| Apresentação | Demo ao vivo e defesa do código. |

**Ponto crítico:** a nota é por arguição individual. O professor pergunta a cada integrante, soma as notas e o grupo redistribui. Quem não souber explicar o código inteiro puxa a nota de todos para baixo, então o plano reserva tempo para que **todos dominem os 6 padrões**, não só a sua parte.

## Tema proposto e mapa dos padrões

Sugestão: **Sistema de Pedidos de Delivery** (lanchonete), em console. É um domínio que todos entendem e onde os 6 padrões surgem de problemas reais, o que facilita a justificativa técnica. Se a equipe preferir outro tema, mantenha a lógica: cada padrão precisa resolver uma dor concreta do sistema.

**Fluxo da aplicação:** cliente monta o pedido → adiciona extras aos itens → escolhe tipo de entrega e cupom → escolhe forma de pagamento → pedido muda de status e notifica cliente e cozinha → relatório de pedidos do dia.

| Categoria | Padrão | Onde entra | Problema que resolve |
| --- | --- | --- | --- |
| Criacional | Builder | `PedidoBuilder` monta o `Pedido` | Pedido tem muitos campos opcionais (itens, endereço, cupom, observação); evita construtores gigantes e valida antes de criar. |
| Criacional | Factory Method | `PagamentoFactory` cria `Pix`, `Cartao`, `Dinheiro` | O menu não precisa conhecer as classes concretas; nova forma de pagamento não altera o código cliente. |
| Estrutural | Decorator | `ExtraDecorator` (queijo, bacon, molho) sobre `ItemCardapio` | Combinações de adicionais sem explosão de subclasses; preço e descrição somam dinamicamente. |
| Estrutural | Adapter | `GatewayPagamentoAdapter` envolve uma `ApiBancoExterna` simulada | API "de terceiros" tem interface incompatível com nossa `Pagamento`; o adapter traduz sem mexer em nenhum dos lados. |
| Comportamental | Strategy | `CalculoFreteStrategy` (retirada, normal, expressa) e `DescontoStrategy` | Regras de frete/desconto trocadas em tempo de execução, sem `if/else` encadeado. |
| Comportamental | Observer | `Pedido` notifica `ClienteNotificador` e `CozinhaPainel` | Quando o status muda, vários interessados reagem sem que o `Pedido` conheça cada um (baixo acoplamento). |

**Reservas**, caso o professor questione algum: State (status do pedido), Composite (combos) e Facade (`PedidoService` simplificando o checkout). Singleton deve ser evitado como "padrão de enchimento".

**GRASP no design:** `PedidoController` como Controller entre menu e domínio; `Pedido` como Information Expert do próprio total; `PedidoBuilder` como Creator; interfaces (`Pagamento`, `CalculoFreteStrategy`) garantindo baixo acoplamento e Polimorfismo; serviços coesos por responsabilidade.

**Extra opcional:** usar Collections (List/Map para cardápio e relatório) e uma Thread simulando o preparo na cozinha, já que foram os últimos conteúdos da disciplina.

## Divisão de papéis

Cada pessoa é dona de uma fatia vertical (código + texto da documentação daquela parte) e revisora da fatia de outra. Integrantes 2 a 5 são placeholders: troque pelos nomes.

| Pessoa | Papel | Entrega no código | Entrega na documentação | Revisa |
| --- | --- | --- | --- | --- |
| Hugo | Líder técnico e integração | Estrutura de pacotes, modelo de domínio (`Pedido`, `ItemCardapio`, `Cliente`), `PedidoController`, `Main` com menu, repositório em memória | Objetivo, problema, funcionalidades, seção GRASP | Integrante 5 |
| Integrante 2 | Criacionais | Builder (`PedidoBuilder`) e Factory Method (`PagamentoFactory` + classes de pagamento) | Justificativa dos 2 criacionais | Integrante 3 |
| Integrante 3 | Estruturais | Decorator (extras do item) e Adapter (gateway de pagamento externo simulado) | Justificativa dos 2 estruturais | Integrante 4 |
| Integrante 4 | Comportamentais | Strategy (frete e desconto) e Observer (notificações de status) | Justificativa dos 2 comportamentais | Integrante 2 |
| Integrante 5 | Qualidade e documentação | Exceções próprias e validações, relatório com Collections, roteiro de testes manuais | Consolida o documento, diagrama UML, seção POO, instruções de execução no Eclipse | Hugo |

Regras da equipe:

- Hugo define interfaces e o modelo de domínio primeiro; os outros programam contra essas interfaces em paralelo.
- Repositório Git único (GitHub), uma branch por pessoa, merge só após revisão do revisor indicado.
- Mesma versão de Java para todos (ex.: Java 17) e projeto Eclipse versionado (`.project` e `.classpath` no Git, `bin/` no `.gitignore`).
- Grupo de WhatsApp para avisos; check-in rápido a cada 2 dias (15 min, online).

## Cronograma

&#91;embedded content: cronograma · 6 fases, entrega em 30/11 (a confirmar)\]

O enunciado não traz data; o plano supõe entrega em 30/11 e deve ser ajustado assim que o professor confirmar.

1. **Kickoff (19–20/11):** reunião de 1h para fechar tema e padrões; registrar a equipe na planilha; Hugo cria o repositório, os pacotes, as interfaces e o modelo de domínio.
2. **Padrões em paralelo (21–24/11):** Integrantes 2, 3 e 4 implementam seus 2 padrões cada; Integrante 5 faz exceções, validações e o relatório.
3. **Integração (25–26/11):** Hugo conecta tudo no `PedidoController` e no menu; o fluxo completo roda de ponta a ponta.
4. **Documentação e UML (25–28/11):** cada dono escreve a seção do seu padrão; Integrante 5 consolida e desenha o UML.
5. **Congelamento (28/11):** depois disso, só correção de bug.
6. **Estudo e simulado (27–30/11):** walkthrough, rodízio invertido e simulado de arguição.

## Estrutura do projeto no Eclipse

Pacotes por responsabilidade, nomeados pelo que fazem (o professor avalia isso):

```
src/br/ucsal/delivery/
  app/            Main (menu console)
  controller/     PedidoController (GRASP Controller)
  model/          Pedido, ItemCardapio, Cliente, StatusPedido (enum)
  builder/        PedidoBuilder
  pagamento/      Pagamento (interface), Pix, Cartao, Dinheiro, PagamentoFactory
  pagamento/externo/  ApiBancoExterna, GatewayPagamentoAdapter
  decorator/      ExtraDecorator, QueijoExtra, BaconExtra, MolhoExtra
  strategy/       CalculoFreteStrategy, DescontoStrategy e implementações
  observer/       PedidoObserver, ClienteNotificador, CozinhaPainel
  repository/     PedidoRepository (em memória, com Collections)
  exception/      PedidoInvalidoException, PagamentoRecusadoException
```

Convenções: classes em PascalCase, métodos em camelCase com verbo (`calcularTotal`, `alterarStatus`), atributos sempre `private` com getters só quando necessário. Comentário curto no topo de cada classe que implementa padrão: `// Padrão: Decorator — adiciona extras sem subclasses`. Isso ajuda a localizar os padrões na hora da arguição.

POO que precisa aparecer de forma natural: interface (`Pagamento`), classe abstrata (`ExtraDecorator`), herança (extras), composição (`Pedido` contém itens), polimorfismo (`pagamento.processar()`), sobrescrita (`getPreco()` nos decorators, `toString()`), sobrecarga (`adicionarItem(item)` e `adicionarItem(item, quantidade)`).

## Preparação para a arguição

A nota de cada um depende de explicar o projeto inteiro, então o estudo é uma tarefa do plano, não um extra.

1. **Walkthrough do código** (1 encontro, \~1h30): cada dono apresenta sua fatia aos outros 4 abrindo o código no Eclipse.
2. **Rodízio invertido**: cada pessoa explica um padrão que *não* foi ela que fez; o dono corrige.
3. **Banco de perguntas** (Integrante 5 consolida, todos contribuem). Para cada padrão, ter resposta pronta para:
    - Qual problema ele resolve aqui e o que aconteceria sem ele?
    - Mostre no código onde está e quem o usa.
    - Por que este padrão e não outro parecido? (Decorator × herança, Strategy × State, Factory Method × Builder, Adapter × Facade)
    - Como adicionar uma nova opção (novo pagamento, novo frete, novo extra) sem alterar o código existente?
4. **Perguntas de GRASP e POO**: onde está o Controller, quem é o Creator do pedido, onde há polimorfismo, sobrecarga × sobrescrita, interface × classe abstrata, como o acoplamento ficou baixo.
5. **Simulado final**: um integrante faz o papel do professor e sorteia perguntas para cada colega, 5 minutos por pessoa.
6. **Demo ensaiada**: roteiro fixo que passa pelos 6 padrões em menos de 5 minutos, rodado na máquina que será usada na apresentação.

## Checklist de entrega

- [ ] Equipe registrada na planilha [Formação Equipes – POA – Noturno](https://docs.google.com/spreadsheets/d/1nANo7ro3cv6aSp0ZVnXqbOYDLgh0TG1mVFFHMfrXbOE/edit) (máx. 4 equipes na turma; as abas pareciam vazias em 18/11)
- [ ] Os 6 padrões rodando dentro do fluxo principal, não em `main`s separados
- [ ] Projeto importa em máquina limpa via *File → Import → Existing Projects into Workspace* e roda sem erro
- [ ] Nenhuma dependência externa (ou JARs incluídos no projeto)
- [ ] Documentação técnica com as 8 seções pedidas (nome/objetivo, problema, funcionalidades, 6 padrões por categoria, onde/por quê, GRASP, POO, como executar)
- [ ] Diagrama de classes UML com os padrões identificados (sugestão: draw.io, PlantUML ou ObjectAid no Eclipse)
- [ ] Roteiro de demo testado
- [ ] Simulado de arguição feito com os 5

**A confirmar com o professor** (esta semana ele está atendendo dúvidas, conforme aviso de 16/11):

- Data de entrega e da apresentação: não estão no enunciado. O cronograma acima supõe entrega até 30/11; ajuste quando souber.
- Formato de envio (zip do projeto no Classroom? link do GitHub?).
- Se o tema escolhido precisa de aprovação prévia.
- Se interface console é aceita (o enunciado não exige interface gráfica).s