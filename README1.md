/
├── README.md
├── pom.xml
├── src/
│   ├── main/java/com/estacionamento/
│   │   ├── domain/
│   │   │   ├── Usuario.java
│   │   │   ├── Funcionario.java
│   │   │   ├── Visitante.java
│   │   │   ├── Veiculo.java
│   │   │   ├── Movimentacao.java
│   │   │   ├── Vaga.java
│   │   │   ├── StatusVaga.java
│   │   │   ├── Reserva.java
│   │   │   ├── Cobranca.java
│   │   │   ├── SituacaoPagamento.java
│   │   │
│   │   ├── factory/
│   │   │   └── VeiculoFactory.java
│   │   │
│   │   ├── pattern/
│   │   │   ├── singleton/
│   │   │   │   ├── GestorVagas.java
│   │   │   │   ├── IntegracaoRH.java
│   │   │   │   └── ReconhecimentoPlacas.java
│   │   │   ├── strategy/
│   │   │   │   ├── ICalculoCobranca.java
│   │   │   │   ├── CalculoMensalista.java
│   │   │   │   └── CalculoAvulso.java
│   │   │   └── observer/
│   │   │       ├── ICameraObserver.java
│   │   │       └── CameraOCR.java
│   │   │
│   │   ├── repository/
│   │   │   ├── FuncionarioRepo.java
│   │   │   ├── VisitanteRepo.java
│   │   │   ├── VeiculoRepo.java
│   │   │   └── MovimentacaoRepo.java
│   │   │
│   │   └── service/
│   │       ├── RegistroEntradaService.java
│   │       └── RegistroSaidaService.java
│
└── test/java/com/estacionamento/
    ├── strategy/
    │   ├── CalculoMensalistaTest.java
    │   └── CalculoAvulsoTest.java
    ├── factory/
    │   └── VeiculoFactoryTest.java
    └── singleton/
        └── GestorVagasTest.java
