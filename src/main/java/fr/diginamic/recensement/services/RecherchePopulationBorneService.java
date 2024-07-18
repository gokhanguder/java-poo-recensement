package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 *
 * @author DIGINAMIC
 */
public class RecherchePopulationBorneService extends MenuService {

    @Override
    public void traiter(Recensement rec, Scanner scanner) {

        try {
            System.out.println("Quel est le code du département recherché ? ");
            String choix = scanner.nextLine();

            verifierEntier(choix, "département");

            boolean departementExiste = rec.getVilles().stream()
                    .anyMatch(ville -> ville.getCodeDepartement().equalsIgnoreCase(choix));
            if (!departementExiste) {
                throw new IllegalArgumentException("Code de département inconnu.");
            }

            System.out.println("Choississez une population minimum (en milliers d'habitants): ");
            String saisieMin = scanner.nextLine();

            verifierEntier(saisieMin, "minimum");

            int min = Integer.parseInt(saisieMin) * 1000;

            if (min < 0) {
                throw new IllegalArgumentException("Les valeurs minimum doivent être positives.");
            }

            System.out.println("Choississez une population maximum (en milliers d'habitants): ");
            String saisieMax = scanner.nextLine();

            verifierEntier(saisieMax, "maximum");

            int max = Integer.parseInt(saisieMax) * 1000;

            if (max < 0) {
                throw new IllegalArgumentException("Les valeurs maximum doivent être positives.");
            }

            if (min > max) {
                throw new IllegalArgumentException("Le minimum ne peut pas être supérieur au maximum.");
            }

            List<Ville> villes = rec.getVilles();
            boolean villeTrouvee = false; // Variable pour suivre si une ville correspondante a été trouvée
            for (Ville ville : villes) {
                if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
                    if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
                        System.out.println(ville);
                        villeTrouvee = true;
                    }
                }
            }

            // Si aucune ville correspondante n'a été trouvée, afficher un message
            if (!villeTrouvee) {
                System.out.println("Aucune ville ne correspond aux critères de recherche spécifiés!");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    private void verifierEntier(String input, String type) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La valeur du " + type + " doit être un nombre entier.");
        }
    }
}