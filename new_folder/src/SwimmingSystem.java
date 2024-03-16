import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SwimmingSystem {
	private List<Lesson> lessons;
	private List<Coach> coaches;
	private List<Learner> learners;

	public SwimmingSystem() {
		this.lessons = new ArrayList<>();
		this.coaches = new ArrayList<>();
		this.learners = new ArrayList<>();
	}

	public void addLesson(Lesson lesson) {
		lessons.add(lesson);
	}

	public void addNewLesson() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the day of the lesson:");
		String day = scanner.nextLine();
		System.out.println("Enter the time of the lesson:");
		String time = scanner.nextLine();
		System.out.println("Enter the grade level of the lesson:");
		int gradeLevel = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		System.out.println("Enter the coach of the lesson:");
		String coach = scanner.nextLine();
		System.out.println("Enter the capacity of the lesson:");
		int capacity = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		Lesson lesson = new Lesson(day, time, gradeLevel, coach, capacity);
		addLesson(lesson);
		System.out.println("Lesson added successfully.");
		// scanner.close();
	}

	public void addCoach(Coach coach) {
		coaches.add(coach);
	}

	public void addNewCoach() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the name of the coach:");
		String name = scanner.nextLine();
		Coach coach = new Coach(name);
		addCoach(coach);
		System.out.println("Coach added successfully.");
		//// scanner.close();
	}

	public void addLearner(Learner learner) {
		learners.add(learner);
	}

	public void displayTimetableByGradeLevel(int gradeLevel) {
		System.out.println("Available Lessons for Grade Level " + gradeLevel + ":");
		for (Lesson lesson : lessons) {
			if (lesson.getGradeLevel() == gradeLevel) {
				System.out.println("Day: " + lesson.getDay() + ", Time: " + lesson.getTime() + ", Coach: "
						+ lesson.getCoach() + ", Capacity: " + lesson.getCapacity());
			}
		}
	}

	public void bookLesson(String learnerName, int gradeLevel) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Available Lessons for Grade Level " + gradeLevel + ":");
		displayTimetableByGradeLevel(gradeLevel);
		System.out.println("Enter the time (e.g., 4-5pm) of the lesson you want to book:");
		String lessonTime = scanner.nextLine();

		Lesson selectedLesson = findLessonByGradeLevelAndTime(gradeLevel, lessonTime);
		if (selectedLesson != null) {
			Learner learner = findLearner(learnerName);
			if (learner != null) {
				if (selectedLesson.addLearner(learner)) {
					learner.bookLesson(selectedLesson);
					System.out.println(learnerName + " has successfully booked the lesson.");
				} else {
					System.out.println("The lesson is already full. Booking failed.");
				}
			} else {
				System.out.println("Learner not found.");
			}
		} else {
			System.out.println("Lesson not found.");
		}
		// scanner.close();
	}

	private Lesson findLessonByGradeLevelAndTime(int gradeLevel, String lessonTime) {
		for (Lesson lesson : lessons) {
			if (lesson.getGradeLevel() == gradeLevel && lesson.getTime().equalsIgnoreCase(lessonTime)) {
				return lesson;
			}
		}
		return null;
	}

	private Learner findLearner(String learnerName) {
		for (Learner learner : learners) {
			if (learner.getName().equalsIgnoreCase(learnerName)) {
				return learner;
			}
		}
		return null;
	}

	public void displayTimetable(String day, String filterValue) {
		System.out.println("Timetable for " + day + " | Filter: " + filterValue);
		System.out.println("------------------------------------------------------------");
		System.out.println("Day\tTime\tGrade Level\tCoach\tCapacity");
		System.out.println("------------------------------------------------------------");
		for (Lesson lesson : lessons) {
			boolean match = false;
			if (day.equalsIgnoreCase("day")) {
				match = lesson.getDay().equalsIgnoreCase(filterValue);
			} else if (day.equalsIgnoreCase("grade level")) {
				match = lesson.getGradeLevel() == Integer.parseInt(filterValue);
			} else if (day.equalsIgnoreCase("coach")) {
				match = lesson.getCoach().equalsIgnoreCase(filterValue);
			}

			if (match) {
				System.out.println(lesson.getDay() + "\t" + lesson.getTime() + "\t" + lesson.getGradeLevel() + "\t\t"
						+ lesson.getCoach() + "\t" + lesson.getCapacity());
			}
		}
		System.out.println("------------------------------------------------------------");
	}

	public void registerNewLearner() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter learner's name:");
		String name = scanner.nextLine();

		System.out.println("Enter learner's gender:");
		String gender = scanner.nextLine();

		System.out.println("Enter learner's age:");
		int age = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		System.out.println("Enter learner's emergency contact:");
		String emergencyContact = scanner.nextLine();

		System.out.println("Enter learner's current grade level:");
		int currentGrade = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		Learner learner = new Learner(name, gender, age, emergencyContact, currentGrade);
		addLearner(learner);

		System.out.println("New learner registered successfully!");
		// scanner.close();
	}

	public void attendSwimmingLesson(String learnerName) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter the day of the lesson:");
		String day = scanner.nextLine();

		System.out.println("Enter the time slot of the lesson:");
		String timeSlot = scanner.nextLine();

		System.out.println("Enter the grade level of the lesson:");
		int gradeLevel = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		Lesson lesson = findLessonByDayTimeGrade(day, timeSlot, gradeLevel);
		if (lesson != null) {
			Learner learner = findLearner(learnerName);
			if (learner != null) {
				if (lesson.getLearners().contains(learner)) {
					lesson.getLearners().remove(learner);
					learner.attendLesson(lesson);
					System.out.println(learnerName + " attended the lesson successfully.");
				} else {
					System.out.println(learnerName + " is not booked for this lesson.");
				}
			} else {
				System.out.println("Learner not found.");
			}
		} else {
			System.out.println("Lesson not found.");
		}
		// scanner.close();
	}

	private Lesson findLessonByDayTimeGrade(String day, String timeSlot, int gradeLevel) {
		for (Lesson lesson : lessons) {
			if (lesson.getDay().equalsIgnoreCase(day) && lesson.getTime().equalsIgnoreCase(timeSlot)
					&& lesson.getGradeLevel() == gradeLevel) {
				return lesson;
			}
		}
		return null;
	}

	public void changeOrCancelBooking(String learnerName) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter the day of the lesson:");
		String day = scanner.nextLine();

		System.out.println("Enter the time slot of the lesson:");
		String timeSlot = scanner.nextLine();

		System.out.println("Enter the grade level of the lesson:");
		int gradeLevel = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		Lesson lesson = findLessonByDayTimeGrade(day, timeSlot, gradeLevel);
		if (lesson != null) {
			Learner learner = findLearner(learnerName);
			if (learner != null) {
				if (lesson.getLearners().contains(learner)) {
					System.out.println("Do you want to change or cancel the booking?");
					System.out.println("1. Change booking");
					System.out.println("2. Cancel booking");
					int choice = scanner.nextInt();
					scanner.nextLine(); // Consume newline
					switch (choice) {
					case 1:
						changeBooking(learner, lesson);
						break;
					case 2:
						cancelBooking(learner, lesson);
						break;
					default:
						System.out.println("Invalid choice.");
					}
				} else {
					System.out.println(learnerName + " is not booked for this lesson.");
				}
			} else {
				System.out.println("Learner not found.");
			}
		} else {
			System.out.println("Lesson not found.");
		}
		// scanner.close();
	}

	private void changeBooking(Learner learner, Lesson lesson) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the new day of the lesson:");
		String newDay = scanner.nextLine();

		System.out.println("Enter the new time slot of the lesson:");
		String newTimeSlot = scanner.nextLine();

		System.out.println("Enter the new grade level of the lesson:");
		int newGradeLevel = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		Lesson newLesson = findLessonByDayTimeGrade(newDay, newTimeSlot, newGradeLevel);
		if (newLesson != null) {
			if (newLesson.addLearner(learner)) {
				lesson.getLearners().remove(learner);
				learner.changeBooking(lesson, newLesson);
				System.out.println("Booking changed successfully.");
			} else {
				System.out.println("The new lesson is already full. Booking change failed.");
			}
		} else {
			System.out.println("New lesson not found.");
		}
		// scanner.close();
	}

	private void cancelBooking(Learner learner, Lesson lesson) {
		lesson.getLearners().remove(learner);
		learner.cancelBooking(lesson);
		System.out.println("Booking canceled successfully.");
	}

	public void generateMonthlyReport() {
		// Loop through all learners
		for (Learner learner : learners) {
			// Print learner's name
			System.out.println("Learner: " + learner.getName());

			// Initialize counters for learner's booked, canceled, and attended lessons
			int learnerBookedCount = 0;
			int learnerCanceledCount = 0;
			int learnerAttendedCount = 0;

			// Loop through learner's booked lessons
			System.out.println("Booked lessons:");
			for (Lesson lesson : learner.getBookedLessons()) {
				System.out.println(" - " + lesson.getDay() + " " + lesson.getTime());
				learnerBookedCount++;
			}

			// Loop through learner's canceled lessons
			System.out.println("Canceled lessons:");
			for (Lesson lesson : learner.getCanceledLessons()) {
				System.out.println(" - " + lesson.getDay() + " " + lesson.getTime());
				learnerCanceledCount++;
			}

			// Loop through learner's attended lessons
			System.out.println("Attended lessons:");
			for (Lesson lesson : learner.getAttendedLessons()) {
				System.out.println(" - " + lesson.getDay() + " " + lesson.getTime());
				learnerAttendedCount++;
			}

			// Print learner's total counts
			System.out.println("Total booked: " + learnerBookedCount);
			System.out.println("Total canceled: " + learnerCanceledCount);
			System.out.println("Total attended: " + learnerAttendedCount);
			System.out.println();
		}
	}

	public void generateMonthlyReportForCoach() {
		System.out.println("Monthly Coach Report:");
		for (Coach coach : coaches) {
			System.out.println("Coach: " + coach.getName());
			int totalRatings = 0;
			int numberOfRatings = 0;
			for (Lesson lesson : coach.getLessonsTaught()) {
				System.out.println("Lesson: " + lesson.getDay() + " " + lesson.getTime());
				List<Learner> learners = lesson.getLearners();
				for (Learner learner : learners) {
					// Assuming rating is provided by the learner after attending the lesson
					int rating = learner.getRatingForLesson(lesson);
					if (rating != -1) { // -1 indicates no rating provided
						totalRatings += rating;
						numberOfRatings++;
						System.out.println(" - Rating: " + rating);
					}
				}
			}
			if (numberOfRatings > 0) {
				double averageRating = (double) totalRatings / numberOfRatings;
				System.out.println("Average Rating: " + averageRating);
			} else {
				System.out.println("No ratings available.");
			}
			System.out.println();
		}
	}

}
