package stack;

import interfaces.InterfaceCliente;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import principal.Hotel;
import principal.Trip;

/**
 * Regra de negócio
 */
public class LeiaCSV {
    /**
     * Retorna uma passagem
     * @param ida false só ida, true ida e volta
     * @param source da onde está viajanto
     * @param destination para onde está viajando
     * @param dateIda data de ida do voo
     * @param passagens numero de passagenes desejadas
     * @param dateVolta data do voo de volta
     * @return a passagem caso encontrada
     */
    public static Trip searchTrip(boolean ida, String source, String destination, String dateIda, String dateVolta, int passagens) {
        Trip result = new Trip();
        //Path do arquivo de passagens
        String arquivoCSV = "C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\rmiServer\\src\\stack\\passagem.csv";
        BufferedReader br = null;
        String linha = "";
        //Divisor de strings
        String csvDivisor = ",";
        try {
            //Le arquivo
            br = new BufferedReader(new FileReader(arquivoCSV));
            //Le linha do arquivo
            while ((linha = br.readLine()) != null) {
                //pega linha e divide em strings
                String[] resultCSV = linha.split(csvDivisor);
                //se os dados são iguais o da viagem, põe na viagem
                if (resultCSV[0].equals(source) && resultCSV[1].equals(destination)
                    && LocalDate.parse(resultCSV[2]).equals(LocalDate.parse(dateIda))
                    && Integer.valueOf(resultCSV[3]) >= passagens) {
                    //se é tudo igual tem a viagem
                    result.setSource(resultCSV[0]);
                    result.setDestination(resultCSV[1]);
                    result.setBegin(LocalDate.parse(resultCSV[2]));
                    break;
                }
            }
            //Se é ida e volta
            if (ida) {
                //Le arquivo
                br = new BufferedReader(new FileReader(arquivoCSV));
                //Le linha
                while ((linha = br.readLine()) != null) {
                    //pega linha e divide em strings
                    String[] resultCSV = linha.split(csvDivisor);
                    //se os dados são iguais o da viagem, põe na viagem
                    if (resultCSV[0].equals(destination) && resultCSV[1].equals(source)
                        && LocalDate.parse(resultCSV[2]).equals(LocalDate.parse(dateVolta))
                        && Integer.valueOf(resultCSV[3]) >= passagens) {
                        //se é tudo igual tem a viagem
                        result.setEnd(LocalDate.parse(resultCSV[2]));
                        result.setRoundTrip(ida);
                        break;
                    }
                }
            }
            //Se é ida e volta e não tem os dados, retorna nulo
            if (ida && (result.getBegin() == null || result.getEnd() == null)) {
                result = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * Retorna uma passagem
     * @param ida false só ida, true ida e volta
     * @param source da onde está viajanto
     * @param destination para onde está viajando
     * @param dateIda data de ida do voo
     * @param passagens numero de passagenes desejadas
     * @param dateVolta data do voo de volta
     * @return a passagem caso encontrada
     */
    public synchronized static boolean buyTrip(boolean ida, String source, String destination, String dateIda, String dateVolta, int passagens) {
        boolean result = false;
        boolean flag1 = false;
        boolean flag2 = false;
        String arquivoCSV = "C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\rmiServer\\src\\stack\\passagem.csv";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ",";
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((linha = br.readLine()) != null) {
                String[] resultCSV = linha.split(csvDivisor);
                if (resultCSV[0].equals(source) && resultCSV[1].equals(destination)
                    && LocalDate.parse(resultCSV[2]).equals(LocalDate.parse(dateIda))
                    && Integer.valueOf(resultCSV[3]) >= passagens) {
                    flag1 = true;
                    break;
                }
            }
            br.close();
            if (ida) {
                br = new BufferedReader(new FileReader(arquivoCSV));
                while ((linha = br.readLine()) != null) {
                    String[] resultCSV = linha.split(csvDivisor);
                    if (resultCSV[0].equals(destination) && resultCSV[1].equals(source)
                        && LocalDate.parse(resultCSV[2]).equals(LocalDate.parse(dateVolta))
                        && Integer.valueOf(resultCSV[3]) >= passagens) {
                        //se é tudo igual tem a viagem
                        flag2 = true;
                        break;
                    }
                }
            }
            br.close();
            if ((ida && (flag1 && flag2)) || (!ida && flag1)) {
                br = new BufferedReader(new FileReader(arquivoCSV));
                while ((linha = br.readLine()) != null) {
                    String[] resultCSV = linha.split(csvDivisor);
                    if (resultCSV[0].equals(source) && resultCSV[1].equals(destination)
                        && LocalDate.parse(resultCSV[2]).equals(LocalDate.parse(dateIda))
                        && Integer.valueOf(resultCSV[3]) >= passagens && flag1) {
                        resultCSV[3] = String.valueOf((Integer.valueOf(resultCSV[3]) - passagens));
                        linha = Arrays.toString(resultCSV);
                        linha = linha.replace("[", "");
                        linha = linha.replace("]", "");
                        flag1 = false;
                    }
                    if (ida && (resultCSV[0].equals(destination) && resultCSV[1].equals(source)
                        && LocalDate.parse(resultCSV[2]).equals(LocalDate.parse(dateVolta))
                        && Integer.valueOf(resultCSV[3]) >= passagens && flag2)) {
                        resultCSV[3] = String.valueOf((Integer.valueOf(resultCSV[3]) - passagens));
                        linha = Arrays.toString(resultCSV);
                        linha = linha.replace("[", "");
                        linha = linha.replace("]", "");
                        flag2 = false;
                    }
                    sb.append(linha);
                    sb.append("\n");
                }
                String end = sb.substring(0, sb.length() - 1);
                end = end.replace(" ", "");
                br.close();
                FileWriter file = new FileWriter(arquivoCSV);
                file.write(end);
                file.flush();
                file.close();
            }
            if (!flag1 && !flag2) {
                result = true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = false;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    public static Hotel searchHotel(String name, String flightdate, String flightdateVolta, int numberOfRooms, int numberOfPeople) {
        Hotel result = new Hotel();
        String hotelCSV = "C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\rmiServer\\src\\stack\\hotel.csv";
        String quartoCSV = "C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\rmiServer\\src\\stack\\quarto.csv";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ",";
        //id do hotel
        int original = numberOfRooms;
        int id = 0;
        int price = 0;
        try {
            br = new BufferedReader(new FileReader(hotelCSV));
            while ((linha = br.readLine()) != null) {
                String[] resultCSV = linha.split(csvDivisor);
                if (resultCSV[0].equals(name)) {
                    //se é tudo igual tem o hotel
                    id = Integer.valueOf(resultCSV[2]);
                    result.setCity(resultCSV[0]);
                    price = Integer.valueOf(resultCSV[1]);
                    break;
                }
            }
            if (id != 0) {
                br = new BufferedReader(new FileReader(quartoCSV));
                while ((linha = br.readLine()) != null && numberOfRooms > 0) {
                    String[] resultCSV = linha.split(csvDivisor);
                    if (Integer.valueOf(resultCSV[0]) == id
                        && LocalDate.parse(resultCSV[3]).isBefore(LocalDate.parse(flightdate))) {
                        numberOfRooms--;
                    }
                }
                if (numberOfRooms > 0) {
                    result = null;
                } else {
                    result.setPrice(price * original);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = null;
        } catch (IOException e) {
            result = null;
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    public synchronized static boolean buyHotel(String name, String flightdate, String flightdateVolta, int numberOfRooms, int numberOfPeople) {
        boolean result = false;
        String hotelCSV = "C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\rmiServer\\src\\stack\\hotel.csv";
        String quartoCSV = "C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\rmiServer\\src\\stack\\quarto.csv";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ",";
        //id do hotel
        int original = numberOfRooms;
        int id = 0;
        int price = 0;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(hotelCSV));
            while ((linha = br.readLine()) != null) {
                String[] resultCSV = linha.split(csvDivisor);
                if (resultCSV[0].equals(name)) {
                    //se é tudo igual tem o hotel
                    id = Integer.valueOf(resultCSV[2]);
                    price = Integer.valueOf(resultCSV[1]);
                    break;
                }
            }
            if (id != 0) {
                br = new BufferedReader(new FileReader(quartoCSV));
                while ((linha = br.readLine()) != null && numberOfRooms > 0) {
                    String[] resultCSV = linha.split(csvDivisor);
                    if (Integer.valueOf(resultCSV[0]) == id
                        && LocalDate.parse(resultCSV[3]).isBefore(LocalDate.parse(flightdate))) {
                        numberOfRooms--;
                    }
                }
                if (numberOfRooms > 0) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (result) {
                numberOfRooms = original;
                br = new BufferedReader(new FileReader(quartoCSV));
                while ((linha = br.readLine()) != null) {
                    String[] resultCSV = linha.split(csvDivisor);
                    if (Integer.valueOf(resultCSV[0]) == id
                        && LocalDate.parse(resultCSV[3]).isBefore(LocalDate.parse(flightdate))
                        && numberOfRooms > 0) {
                        resultCSV[2] = flightdate;
                        resultCSV[3] = flightdateVolta;
                        linha = Arrays.toString(resultCSV);
                        linha = linha.replace("[", "");
                        linha = linha.replace("]", "");
                        numberOfRooms--;
                    }
                    sb.append(linha);
                    sb.append("\n");
                }
                String end = sb.substring(0, sb.length() - 1);
                end = end.replace(" ", "");
                br.close();
                FileWriter file = new FileWriter(quartoCSV);
                file.write(end);
                file.flush();
                file.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = false;
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    public static void registrar(String event, InterfaceCliente client, String destination, Integer price) {
        String registro = "C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\rmiServer\\src\\stack\\interesse.csv";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ",";
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(registro));
            while ((linha = br.readLine()) != null) {
                sb.append(linha);
                sb.append("\n");
            }
            br.close();
            sb.append(event).append(",").append(client).append(",").append(destination).append(",").append(price);
            FileWriter file = new FileWriter(registro);
            file.write(sb.toString());
            file.flush();
            file.close();
        } catch (Exception ex) {
        }
    }

}
