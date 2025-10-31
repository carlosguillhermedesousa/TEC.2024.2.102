package application.util;

public class coment {
	/*Image cursorImage = new Image(
    metodo.class.getResourceAsStream("/application/view/f1_azul.png"),
    32, // largura desejada
    32, // altura desejada
    true, // preservar proporção
    true  // suavizar imagem
);

ImageCursor customCursor = new ImageCursor(cursorImage, 16, 16); // hotspot no centro
scene.setCursor(customCursor);


Dica sobre o hotspot
O hotspot é o ponto ativo do cursor (geralmente a ponta ou centro). Ele é definido com:

java
new ImageCursor(image, hotspotX, hotspotY);
Se você quer que o clique ocorra no centro da imagem, use:

java
hotspotX = image.getWidth() / 2;
hotspotY = image.getHeight() / 2;



_________________


Como posicionar a imagem ao lado do cursor
No seu código, você está usando:

java
new ImageCursor(cursorImage, cursorImage.getWidth() / 2, cursorImage.getHeight() / 2);
Isso centraliza a imagem no ponteiro. Para que a imagem fique ao lado direito do cursor, você pode definir o hotspot no canto esquerdo da imagem:

java
new ImageCursor(cursorImage, 0, cursorImage.getHeight() / 2);
Ou, se quiser que a imagem fique acima do cursor:

java
new ImageCursor(cursorImage, cursorImage.getWidth() / 2, cursorImage.getHeight());
🧪 Exemplo ajustado
java
public static void cursorMouse(Scene scene) {
    Image cursorImage = new Image(metodo.class.getResourceAsStream("/application/view/f1_vermelho.png"),
                                  32, 32, true, true); // Redimensiona se necessário
    ImageCursor imagemCursor = new ImageCursor(cursorImage, 0, cursorImage.getHeight() / 2); // imagem ao lado direito
    scene.setCursor(imagemCursor);
}
🧠 Dica visual
hotspotX = 0 → imagem começa à direita do cursor

hotspotY = image.getHeight() / 2 → imagem alinhada verticalmente ao centro do cursor

Você pode ajustar esses valores para posicionar a imagem exatamente onde quiser em relação ao ponteiro.
*/
 	//InputStream imagemStream = metodo.class.getResourceAsStream("/application/view/f1_azul.png");
 	//Image imagem = new Image(imagemStream);	
	
}
