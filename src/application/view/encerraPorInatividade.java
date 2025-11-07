package application.view;

public class encerraPorInatividade {
	/*
	 //ScheduledExecutorService que verifica inatividade
	ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	final long[] ultimoMovimento = {System.currentTimeMillis()};

	scheduler.scheduleAtFixedRate(() -> {
	    long agora = System.currentTimeMillis();
	    long minutosInativo = (agora - ultimoMovimento[0]) / 60000;

	    if (minutosInativo >= 10) { // Exemplo: 10 minutos de inatividade
	        Platform.runLater(() -> {
	            Sessao.encerrar();
	            try {
	                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
	                Stage stageLogin = new Stage();
	                stageLogin.setScene(new Scene(root));
	                stageLogin.initStyle(StageStyle.UNDECORATED);
	                stageLogin.centerOnScreen();
	                stageLogin.show();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	        scheduler.shutdown();
	    }
	}, 0, 1, TimeUnit.MINUTES);
	
	//Adicione listeners de atividade na Scene principal
	scene.addEventFilter(MouseEvent.ANY, e -> ultimoMovimento[0] = System.currentTimeMillis());
	scene.addEventFilter(KeyEvent.ANY, e -> ultimoMovimento[0] = System.currentTimeMillis());

*/
}
