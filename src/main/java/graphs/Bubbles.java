package graphs;


import java.util.*;

/**
 * Sophie and Marc want to reduce the bubbles of contacts in the belgian population
 * to contain an evil virus (weird idea but nevertheless inspired by a true belgian story in 2020, don't ask ...).
 *
 * Help them!
 *
 * The Belgian government has imposed on the population to limit the number of contacts, via "bubbles".
 *
 * The principle is quite simple. If you have a (close) contact with someone,
 * You are then in his bubble, and he is in yours.
 *
 * Let's say the following contacts have taken place: [
 * [Alice, Bob], [Alice, Carol], [Carol, Alice], [Eve, Alice], [Alice, Frank],
 * [Bob, Carole], [Bob, Eve], [Bob, Frank], [Bob, Carole], [Eve, Frank]
 * ].
 *
 * Note that the contacts are two-by-two and can occur several times. The order within
 * of a contact does not matter.
 *
 * The resulting bubbles are :
 *
 * - Alice's bubble = [Bob, Carol, Eve, Frank]
 * - Bob's bubble = [Alice, Carol, Eve, Frank]
 * - Carole's bubble = [Alice, Bob]
 * - Frank's Bubble = [Alice, Bob, Eve]
 *
 * Note that the relationship is symmetric
 * (if Alice is in Bob's bubble, then Bob is in Alice's bubble)
 * but not transitive (if Bob is in Alice's bubble, then Bob is in Alice's bubble)
 * and Carol is in Bob's bubble, Carol is not necessarily in Alice's.
 *
 * Since at most n people can be in someone's bubble without him being outlaw
 * given a list of contacts, select pairs of people that you will forbid to meet, so that eventually
 * each person has a bubble of NO MORE than n people (not counting themselves).
 * You need to ban AS FEW AS POSSIBLE (pairs of) people to meet.
 *
 * For example, if n=3, in the example above, you could forbid Alice and Carol to see each other, but also
 * Bob and Carol. This removes 2 links (even though Alice and Carol appear twice in the contacts!).
 * But there is a better solution: prevent Alice and Bob from seeing each other, which only removes one link.
 * Finding an algorithm that solves this problem is complex, that's why we give you a rather vague idea of an algorithm:
 *
 * - As long as there are links between two bubbles each "too big", remove one of these links;
 * - Then, as long as there are bubbles that are too big, remove any link connected to one of these bubbles
 * (removing is equivalent to "adding the link to the list of forbidden relationships")
 *
 * Implementing this algorithm as it is a bad idea. Think of an optimal way to implement it.
 *
 * You CANNOT modify the `contacts` list directly (nor the lists inside)
 * If you try, you will receive an UnsupportedOperationException.
 *
 * @returns a list of people you are going to forbid to see each other. There MUST NOT be any duplicates.
 * the order doesn't matter, both within the ForbiddenRelation and in the list.
 */
public class Bubbles {

    public static List<ForbiddenRelation> cleanBubbles(List<Contact> contacts, int n) {
        // create the bubble mapping <People, Bubble>
		HashMap<String, HashSet<String>> mapping = new HashMap<>();

		for (Contact contact: contacts) {
			// add each people
			if (!mapping.containsKey(contact.a)) {
				mapping.put(contact.a, new HashSet<>());
			}

			if (!mapping.containsKey(contact.b)) {
				mapping.put(contact.b, new HashSet<>());
			}

			// add their bubble
			HashSet<String> bubbleOfA = mapping.get(contact.a);
			bubbleOfA.add(contact.b);

			HashSet<String> bubbleOfB = mapping.get(contact.b);
			bubbleOfB.add(contact.a);
		}

		ArrayList<ForbiddenRelation> forbiddenRelations = new ArrayList<>();

		HashMap<String, Integer> distTo = new HashMap<>();
		Queue<String> queue = new ArrayDeque<>();

		for (String contact: mapping.keySet()) {
			distTo.putIfAbsent(contact, 0);
			queue.add(contact);
		}

		while (!queue.isEmpty()) {
			String next = queue.poll();

			for (String contact: mapping.get(next)) {
				if (distTo.get(contact) < n) {
					distTo.replace(contact, distTo.get(contact) + 1);
				} else {
					forbiddenRelations.add(new ForbiddenRelation(next, contact));
				}
			}
		}


		return forbiddenRelations;
    }

}



class Contact {
    public final String a, b;

    public Contact(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }
}

class ForbiddenRelation implements Comparable<ForbiddenRelation> {
    public final String a, b;

    public ForbiddenRelation(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ForbiddenRelation)
            return a.equals(((ForbiddenRelation) obj).a) && b.equals(((ForbiddenRelation) obj).b);
        return false;
    }

    @Override
    public int compareTo(ForbiddenRelation o) {
        if(a.equals(o.a))
            return b.compareTo(o.b);
        return a.compareTo(o.a);
    }
}
