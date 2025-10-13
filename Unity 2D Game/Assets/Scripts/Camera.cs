using UnityEngine;

public class CameraMovement : MonoBehaviour
{
    public Transform player; // Igraè kojeg kamera prati
    public Vector3 offset; // Pomeraj kamere (z za dubinu)
    public float minX; // Minimalna granica kamere po x osi
    public float maxX; // Maksimalna granica kamere po x osi
    public float halfScreenWidth = 5f; // Polovina širine ekrana u jedinicama igre

    void Update()
    {
        // Raèunaj poziciju igraèa u odnosu na trenutnu poziciju kamere
        float playerXRelativeToCamera = player.position.x - transform.position.x;

        // Pomeri kameru ako igraè preðe prag
        if (playerXRelativeToCamera > halfScreenWidth)
        {
            // Igraè prelazi desnu polovinu - pomeraj kameru desno
            float targetX = transform.position.x + (playerXRelativeToCamera - halfScreenWidth);
            transform.position = new Vector3(
                Mathf.Clamp(targetX, minX, maxX),
                transform.position.y,
                offset.z
            );
        }
        else if (playerXRelativeToCamera < -halfScreenWidth)
        {
            // Igraè prelazi levu polovinu - pomeraj kameru levo
            float targetX = transform.position.x + (playerXRelativeToCamera + halfScreenWidth);
            transform.position = new Vector3(
                Mathf.Clamp(targetX, minX, maxX),
                transform.position.y,
                offset.z
            );
        }
    }
}


