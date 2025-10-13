using UnityEngine;

public class ZombieFollower : MonoBehaviour
{
    public Transform player; // Referenca na igraèa
    public float moveSpeed = 2f; // Brzina zombija
    public float chaseRange = 10f; // Maksimalna udaljenost na kojoj zombi poèinje pratiti igraèa
    public float stopDistance = 1.5f; // Udaljenost na kojoj zombi prestaje približavati se
    public Vector3 zombieSize = new Vector3(10f, 10f, 1f); // Velièina zombija

    private float groundY; // Sprema konstantnu Y poziciju tla
    public Animator animator; // Animator za hodanje i napad
    public bool attack; // Praæenje stanja napada
    public bool run; // Praæenje stanja trèanja

    void Start()
    {
        // Spremljena Y koordinata za tlo
        groundY = transform.position.y;

        // Postavi velièinu zombija
        transform.localScale = zombieSize;

        // Provjera animatora
        if (animator == null)
        {
            animator = GetComponent<Animator>();
        }
    }

    void Update()
    {
        if (player == null) return; // Ako igraè nije postavljen, izlazi iz metode

        // Izraèunaj udaljenost izmeðu zombija i igraèa
        float distanceToPlayer = Vector2.Distance(transform.position, player.position);

        if (distanceToPlayer <= (stopDistance + 0.1))
        {
            // Zombi napada igraèa
            animator.SetBool("isWalking", false);
            animator.SetBool("attack", true);
            attack = true;
            run = false;
        }
        else if (distanceToPlayer <= chaseRange)
        {
            // Zombi prati igraèa
            Vector2 direction = (player.position - transform.position).normalized;

            Vector3 targetPosition = new Vector3(
                transform.position.x + direction.x * moveSpeed * Time.deltaTime,
                groundY, // Drži Y konstantnim
                transform.position.z
            );

            transform.position = targetPosition;

            // Okreni zombija prema igraèu
            if (direction.x >= 0)
                transform.localScale = new Vector3(zombieSize.x, zombieSize.y, zombieSize.z); // Gledaj desno
            else
                transform.localScale = new Vector3(-zombieSize.x, zombieSize.y, zombieSize.z); // Gledaj lijevo

            // Postavi animaciju hodanja
            animator.SetBool("isWalking", true);
            animator.SetBool("attack", false);
            attack = false;
            run = true;
        }
        else
        {
            animator.SetBool("isWalking", false);
            animator.SetBool("attack", false);
            attack = false;
            run = false;
        }
    }

    void OnDrawGizmos()
    {
        Gizmos.color = Color.red;
        Gizmos.DrawWireSphere(transform.position, chaseRange); // Domet praæenja
        Gizmos.color = Color.green;
        Gizmos.DrawWireSphere(transform.position, stopDistance); // Zaustavljanje
    }
}


