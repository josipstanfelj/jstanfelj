using UnityEngine;

public class CloudGroupManager : MonoBehaviour
{
    public float speed = 2f; // Brzina kretanja grupe oblaka
    public float resetX = -10f; // Po�etna X pozicija grupe oblaka (leva strana)
    public float endX = 10f; // Kraj X pozicije grupe oblaka (desna strana)
    public float groupWidth = 15f; // �irina grupe oblaka

    void Update()
    {
        // Pomeraj celu grupu oblaka udesno
        transform.Translate(Vector3.right * speed * Time.deltaTime);

        // Proveri da li je cela grupa napustila ekran s desne strane
        if (transform.position.x > endX)
        {
            // Vrati grupu na po�etnu poziciju iza levog kraja ekrana
            Vector3 newPosition = transform.position;
            newPosition.x = resetX - groupWidth; // Stavi grupu odmah iza po�etka
            transform.position = newPosition;
        }
    }
}

