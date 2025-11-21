public class CameraOCR {

    private List<ICameraObserver> observers = new ArrayList<>();

    public void registrarObserver(ICameraObserver o) {
        observers.add(o);
    }

    public void capturarImagem() { }

    public void notificarObservers() { }
}

