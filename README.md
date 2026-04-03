# Projeto de Automacao de Testes

Este projeto automatiza testes do site XPTO com foco na validacao da funcionalidade de busca de artigos. A suite foi estruturada para executar cenarios BDD com Cucumber, navegacao web com Selenium WebDriver, orquestracao com TestNG e gerenciamento de build pelo Maven.

## Objetivo

O objetivo da automacao e validar o comportamento da busca de artigos no site, cobrindo cenarios positivos e negativos, variacoes de entrada do usuario e diferentes formas de disparar a pesquisa pela interface.

## Arquitetura do Projeto

A automacao foi organizada em camadas para separar responsabilidade e facilitar manutencao:

- `src/test/resources/features`
Contem os arquivos `.feature` com os cenarios escritos em Gherkin. E a camada de especificacao funcional do comportamento esperado.

- `src/test/java/steps`
Contem os step definitions que fazem a ponte entre os passos descritos nas features e a implementacao em Java.

- `src/test/java/page`
Implementa o padrao Page Object Model. As classes encapsulam elementos e acoes da interface:
`BasePage` centraliza driver, waits, actions e helper compartilhado.
`BlogPage` representa a home e o fluxo de busca.
`BlogSearchPage` representa a pagina de resultados e suas validacoes.

- `src/test/java/factory`
Centraliza a criacao e o gerenciamento do WebDriver.
`DriverFactory` cria instancias para `CHROME`, `FIREFOX` e `EDGE`.
`DriverManager` usa `ThreadLocal<WebDriver>` para isolar o driver por thread e suportar execucao paralela.

- `src/test/java/hooks`
Implementa os hooks do Cucumber.
`Hooks` inicializa o navegador antes de cada cenario, le o browser via propriedade de sistema, captura evidencias e encerra o driver ao final.

- `src/test/java/runners`
Contem o runner principal da suite.
`TestRunner` integra Cucumber com TestNG e expoe os cenarios via `DataProvider(parallel = true)`, permitindo paralelismo.

- `src/test/java/utils`
Contem classes utilitarias como apoio a interacoes, screenshots e representacao de artigos retornados.

- `src/test/java/config`
Contem suporte a leitura de parametros de execucao.

- `pom.xml`
Centraliza dependencias, plugins de build, configuracao do Surefire, limpeza de artefatos e geracao de relatorios Allure.

## Fluxo de Execucao

O fluxo implementado hoje funciona assim:

1. O `TestRunner` localiza as features e os pacotes de `steps` e `hooks`.
2. O `Hooks @Before` identifica o browser informado por propriedade de sistema (`-Dbrowser`) e cria o driver correspondente.
3. Os steps chamam os page objects para executar a busca e validar resultados.
4. O `Hooks @After` captura screenshot, anexa evidencias e finaliza o driver.
5. O Maven Surefire executa os testes e respeita a configuracao de paralelismo do `DataProvider`.

## Bibliotecas Utilizadas no POM

As principais bibliotecas declaradas no `pom.xml` sao:

- `org.seleniumhq.selenium:selenium-java:4.18.1`
Biblioteca principal para automacao de navegadores e interacao com a interface web.

- `io.cucumber:cucumber-java:7.15.0`
Implementacao do Cucumber para definicao e execucao de cenarios BDD em Java.

- `io.cucumber:cucumber-testng:7.15.0`
Integracao entre Cucumber e TestNG.

- `org.testng:testng:7.9.0`
Framework de execucao de testes usado como base para orquestracao e paralelismo.

- `io.github.bonigarcia:webdrivermanager:6.3.3`
Gerencia automaticamente os binarios dos navegadores, evitando configuracao manual de drivers.

- `io.qameta.allure:allure-testng:2.25.0`
Integracao do TestNG com Allure para geracao de evidencias e relatorios.

- `io.qameta.allure:allure-cucumber7-jvm:2.24.0`
Integracao do Cucumber com Allure.

- `org.slf4j:slf4j-api:2.0.7`
API de logging usada pela automacao.

- `ch.qos.logback:logback-classic:1.5.13`
Implementacao concreta de logging via SLF4J.

## Plugins Maven Utilizados

- `maven-compiler-plugin:3.8.1`
Compila o projeto com Java 17 e encoding UTF-8.

- `maven-surefire-plugin:3.2.5`
Executa os testes automatizados. Esta configurado com `parallel=tests` e com propriedade `dataproviderthreadcount` padrao igual a `2`.

- `maven-clean-plugin:3.2.0`
Remove artefatos anteriores, incluindo diretorios de resultados e relatorios Allure.

- `allure-maven:2.12.0`
Gera relatorios Allure na fase `verify`.

- `maven-javadoc-plugin:3.4.1`
Gera documentacao JavaDoc para o codigo de testes.

## Como Executar o Projeto

O comando base para execucao via Maven e:

```bash
mvn clean test -Dcucumber.filter.tags=@AGI -Dbrowser=chrome -Ddataproviderthreadcount=3
```

### Explicacao dos parametros

- `clean`
Limpa os artefatos da execucao anterior.

- `test`
Executa a suite de testes automatizados.

- `-Dcucumber.filter.tags=@AGI`
Permite selecionar quais testes serao executados por TAG. Voce pode trocar `@AGI` por qualquer outra TAG existente na feature, por exemplo:

```bash
mvn clean test -Dcucumber.filter.tags=@negative -Dbrowser=chrome -Ddataproviderthreadcount=2
```

- `-Dbrowser=chrome`
Define o navegador de execucao. Os browsers suportados pelo projeto sao:
`chrome`
`firefox`
`edge`

Exemplos:

```bash
mvn clean test -Dcucumber.filter.tags=@AGI -Dbrowser=firefox -Ddataproviderthreadcount=3
```

```bash
mvn clean test -Dcucumber.filter.tags=@AGI -Dbrowser=edge -Ddataproviderthreadcount=3
```

- `-Ddataproviderthreadcount=3`
Define a quantidade de testes executados em paralelo pelo `DataProvider` do TestNG/Cucumber.
Se quiser executar mais ou menos testes em paralelo, basta alterar o valor:

```bash
mvn clean test -Dcucumber.filter.tags=@AGI -Dbrowser=chrome -Ddataproviderthreadcount=1
```

```bash
mvn clean test -Dcucumber.filter.tags=@AGI -Dbrowser=chrome -Ddataproviderthreadcount=5
```

## Paralelismo

O projeto suporta execucao paralela de duas formas:

- Via linha de comando
Use `-Ddataproviderthreadcount=QTDE` para informar quantos cenarios devem rodar em paralelo.

- Via configuracao padrao do projeto
No `pom.xml`, o plugin `maven-surefire-plugin` ja possui a propriedade `dataproviderthreadcount` configurada com valor padrao `2`. Se o parametro nao for informado na linha de comando, esse valor padrao sera utilizado.

Tambem existe configuracao paralela em [`testng.xml`](/C:/Users/admin/IdeaProjects/demo_automacao/testng.xml), com `parallel="tests"` e `thread-count="2"`, que pode ser ajustada conforme a estrategia de execucao desejada.

## Execucao via GitHub Actions

O projeto possui um workflow versionado em [`.github/workflows/tests-allure-pages.yml`](/C:/Users/admin/IdeaProjects/demo_automacao/.github/workflows/tests-allure-pages.yml) para:

- executar os testes com Maven
- gerar o relatorio Allure
- publicar o relatorio no GitHub Pages

### O que o workflow faz

1. Faz checkout do repositorio
2. Configura Java 17
3. Executa `mvn clean test`
4. Armazena os artefatos de teste
5. Instala o Allure CLI
6. Gera o HTML do relatorio Allure
7. Publica o conteudo no GitHub Pages

### Como executar pelo GitHub

Voce pode executar de duas formas:

- Automaticamente a cada `push` nas branches `main` ou `master`
- Manualmente pela aba `Actions`, usando o gatilho `workflow_dispatch`

### Parametros do workflow manual

Ao executar manualmente pelo GitHub Actions, o workflow permite informar:

- `cucumber_tag`
Define a TAG a ser executada. Exemplo: `@AGI` ou `@negative`

- `browser`
Define o navegador da execucao. Valores esperados: `chrome`, `firefox` ou `edge`

- `parallel_count`
Define a quantidade de execucoes paralelas do `DataProvider`

### Comando executado no pipeline

O workflow executa a suite com a mesma base usada localmente:

```bash
mvn clean test -Dcucumber.filter.tags=@AGI -Dbrowser=chrome -Ddataproviderthreadcount=3 -Dheadless=true
```

No CI, a execucao ocorre em modo headless para compatibilidade com o ambiente do GitHub Actions.

### Geracao do relatorio Allure

Depois da execucao dos testes, o workflow gera o relatorio Allure a partir do diretorio:

```text
target/allure-results
```

O HTML final e produzido em:

```text
target/allure-report
```

### Como publicar no GitHub Pages

Para que a publicacao funcione no GitHub, configure o repositorio com:

1. Acesse `Settings`
2. Acesse `Pages`
3. Em `Build and deployment`, selecione `Source: GitHub Actions`
4. Salve a configuracao

Depois disso, cada execucao do workflow fara o deploy automatico do relatorio.

### Como acessar o relatorio publicado

Apos a conclusao do workflow, o relatorio podera ser acessado pela URL:

```text
https://<usuario-ou-organizacao>.github.io/<nome-do-repositorio>/
```

Exemplo:

```text
https://seu-usuario.github.io/demo_automacao/
```

O link final publicado tambem fica disponivel:

- no resumo do job de deploy do GitHub Actions
- na secao `Environments` do ambiente `github-pages`

### Observacoes importantes

- O deploy do Pages depende de o repositorio estar publicado no GitHub
- O GitHub Pages precisa estar habilitado com `Source: GitHub Actions`
- O workflow publica a versao mais recente do relatorio a cada nova execucao elegivel

## Estrutura Resumida

```text
src
  test
    java
      config
      factory
      hooks
      page
      runners
      steps
      utils
    resources
      features
pom.xml
testng.xml
```

## Requisitos

Para executar o projeto corretamente, o ambiente deve possuir os seguintes requisitos instalados ou considerados:

- Java 17
- Maven
- Google Chrome, Mozilla Firefox ou Microsoft Edge instalados na maquina
- Allure CLI instalado para gerar e abrir relatorios localmente

### Bibliotecas utilizadas no projeto

- `org.seleniumhq.selenium:selenium-java:4.18.1`
- `io.cucumber:cucumber-java:7.15.0`
- `io.cucumber:cucumber-testng:7.15.0`
- `org.testng:testng:7.9.0`
- `io.github.bonigarcia:webdrivermanager:6.3.3`
- `io.qameta.allure:allure-testng:2.25.0`
- `io.qameta.allure:allure-cucumber7-jvm:2.24.0`
- `org.slf4j:slf4j-api:2.0.7`
- `ch.qos.logback:logback-classic:1.5.13`

### Plugins Maven utilizados

- `org.apache.maven.plugins:maven-compiler-plugin:3.8.1`
- `org.apache.maven.plugins:maven-surefire-plugin:3.2.5`
- `org.apache.maven.plugins:maven-clean-plugin:3.2.0`
- `io.qameta.allure:allure-maven:2.12.0`
- `org.apache.maven.plugins:maven-javadoc-plugin:3.4.1`

### Allure como requisito

O projeto usa integracao com Allure nas bibliotecas e no build Maven:

- `io.qameta.allure:allure-testng:2.25.0`
- `io.qameta.allure:allure-cucumber7-jvm:2.24.0`
- `io.qameta.allure:allure-maven:2.12.0`

Para visualizar os relatorios localmente, e recomendado instalar o Allure CLI.

Exemplos de uso apos a execucao dos testes:

```bash
allure serve target/allure-results
```

```bash
allure generate target/allure-results --clean -o target/allure-report
```

## Observacao

Como a criacao do driver depende do parametro `-Dbrowser`, recomenda-se manter os valores `chrome`, `firefox` ou `edge` para evitar fallback automatico para Chrome quando um valor invalido for informado.
