Goncalo Pinto nº58178
Pedro Pilo nº58179

--- Compilação, Execução e Testagem ---

Este ficheiro contém as duas classes que foram pedidas no enunciado
e também o ficheiro threaded.java, a classe que representa as 
threads do servidor.

-> Compilação
Passo 1: para compilar as classes anteriores, deve-se utilizar o 
seguinte comando no terminal (de notar que a ordem da compilação dos
ficheiros deve ser a abaixo representada):

	$javac threaded.java MyHttpClient.java MyHttpServer.java 
 
Passo 2: deve-se colocar no mesmo ficheiro a classe TestMP1.java e 
compilá-la:

			$javac TestMP1.java

-> Execução
Passo 1: no terminal, lançar a classe servidor, incluindo um número 
de porto TCP que não esteja a ser utilizado:

		    $java MyHttpServer.java 5555

Passo 2: abrir um outro terminal e lançar a classe de teste, 
incluindo no comando o nome do servidor host e o mesmo número de 
porto TCP previamente utilizado:

		  $java TestMP1.java localhost 5555

-> Testagem
Usar o menu interativo do terminal onde foi executada a classe
TestMP1.java para testar as várias capacidades do servidor e a 
comunicação entre servidor e cliente, garantindo que as respostas
do servidor às mensagens enviadas pelo cliente são as esperadas.

--- Funcionalidades ---

O projeto implementa corretamente todas as funcionalidades pedidas 
no enunciado excepto remover as ligaçoes terminadas de modo a dar espaço a outras . 


