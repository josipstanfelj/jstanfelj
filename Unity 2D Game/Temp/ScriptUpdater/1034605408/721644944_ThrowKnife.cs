using UnityEngine;

public class PlayerController : MonoBehaviour
{
    public GameObject knifePrefab;
    public Transform knifeSpawnPoint;
    public float throwForce = 10f;

    private void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            ThrowKnife();
        }
    }

    private void ThrowKnife()
    {
        GameObject knife = Instantiate(knifePrefab, knifeSpawnPoint.position, Quaternion.identity);
        Rigidbody2D rb = knife.GetComponent<Rigidbody2D>();
        if (rb != null)
        {
            rb.linearVelocity = Vector2.up * throwForce;
        }
    }
}

