package application.model;

public class pedidoModel {
	private int quantidade;
	private double valortotal;
	private int volume;
	
	public pedidoModel(
            int quantidade,
            double valortotal,
            int volume){
        this.quantidade = quantidade;
        this.valortotal = valortotal;
        this.volume = volume;}
	
	 // GETTERS E SETTERS

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getValorTotal() { return valortotal; }
    public void setValorTotal(double valortotal) { this.valortotal = valortotal; }

    public int getVolume() { return volume; }
    public void setVolume(int volume) { this.volume = volume; }

}
