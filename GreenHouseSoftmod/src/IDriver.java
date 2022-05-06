interface IDriver {
    int sendCommand(Greenhouse gh, String token, double boilerValue, double sprinklerValue);
}
