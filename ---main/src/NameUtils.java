public class NameUtils {

    public static String extractInitials(String name) throws InvalidNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException("Имя не может быть пустым");
        }

        String cleanedName = name.trim().replaceAll("\\s+", " ");
        String[] nameParts = cleanedName.split(" ");

        if (nameParts.length == 1) {
            return nameParts[0].substring(0, 1).toUpperCase();
        } else {
            String firstInitial = nameParts[0].substring(0, 1).toUpperCase();
            String lastInitial = nameParts[nameParts.length - 1].substring(0, 1).toUpperCase();
            return firstInitial + lastInitial;
        }
    }

    public static String generateFileName(String name, String format) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String safeName = name.replaceAll("[^a-zA-Z0-9а-яА-Я]", "_");
        return String.format("avatar_%s_%s.%s", safeName, timestamp, format.toLowerCase());
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.trim().length() >= 1;
    }
}